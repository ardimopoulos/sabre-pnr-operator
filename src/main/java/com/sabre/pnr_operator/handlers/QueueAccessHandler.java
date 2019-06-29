package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.responses.Response;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RQ.QueueAccessRQ;
import com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RS.QueueAccessRS;
import com.sabre.web_services.wsse.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import static com.sabre.pnr_operator.constants.HandlerConstants.*;
import static com.sabre.pnr_operator.headers.message_header.Action.QUEUE_ACCESS_LLS;
import static java.lang.Boolean.TRUE;

@Component
@Slf4j
public class QueueAccessHandler extends AbstractHandler {

    @Override
    public Response processRequest() {
        log.info("QueueAccessHandler request is being proceed...");
        QueueAccessRS queueAccessRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getQuquqAccessRQ());

            queueAccessRS = (QueueAccessRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(), MessageHeader.class);

            if (!headerProperties.getConversationId().equals(messageHeader.getConversationId())) {
                log.error("SessionClose response returned a different ConversationId.");
                return getFaultyResponse(FAIL, messages.getProperty(ERROR_DESC),
                        messages.getProperty("error.convId"));
            }

            if (!headerProperties.getCpaid().equals(messageHeader.getCPAId())) {
                log.error("SessionClose response returned a different CPAId.");
                return getFaultyResponse(FAIL, messages.getProperty(ERROR_DESC),
                        messages.getProperty("error.cpaid"));
            }

            Security security = (Security) getHeaderElement(soapResponse.getSoapHeader(), Security.class);

            if (!securityRq.getToken().equals(security.getBinarySecurityToken())) {
                log.error("SessionClose response returned a different token");
                return getFaultyResponse(FAIL, messages.getProperty(ERROR_DESC),
                        messages.getProperty("error.token"));
            }

        } catch (Exception e) {
            log.error("Exception while processing the QueueAccessHandler: " + e.getMessage());
            return getFaultyResponse(ERROR, messages.getProperty("queue.access.error"), messages.getProperty("error.general") + e.getMessage());
        }

        log.info("Successfully retrieved QueueAccess Response.");

        return getSuccessfulResponse(SUCCESS, messages.getProperty("queue.access.success"), queueAccessRS.getApplicationResults().getStatus().value());
    }

    private SoapMessage sendAndReceive(QueueAccessRQ queueAccessRQ) {
        return webServiceTemplate.sendAndReceive(
                webServiceMessage -> {
                    SoapHeader soapHeader = ((SoapMessage) webServiceMessage).getSoapHeader();
                    webServiceTemplate.getMarshaller().marshal(messageHeaderRq.getMessageHeader(QUEUE_ACCESS_LLS), soapHeader.getResult());
                    webServiceTemplate.getMarshaller().marshal(securityRq.getSecurityHeader(), soapHeader.getResult());
                    MarshallingUtils.marshal(webServiceTemplate.getMarshaller(), queueAccessRQ, webServiceMessage);
                },
                webServiceMessage -> (SoapMessage) webServiceMessage
        );
    }

    private QueueAccessRQ getQuquqAccessRQ() {
        QueueAccessRQ queueAccessRQ = new QueueAccessRQ();
        QueueAccessRQ.QueueIdentifier queueIdentifier = new QueueAccessRQ.QueueIdentifier();
        QueueAccessRQ.QueueIdentifier.List list = new QueueAccessRQ.QueueIdentifier.List();
        list.setInd(TRUE);
        queueIdentifier.setList(list);
        queueAccessRQ.setQueueIdentifier(queueIdentifier);

        return queueAccessRQ;
    }
}
