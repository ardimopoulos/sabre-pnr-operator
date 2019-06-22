package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.responses.Response;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.sessionClose.sessionCloseRQ.SessionCloseRQ;
import com.sabre.web_services.sessionClose.sessionCloseRS.SessionCloseRS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import static com.sabre.pnr_operator.constants.HandlerConstants.*;
import static com.sabre.pnr_operator.headers.message_header.Action.SESSION_CLOSE;

@Component
@Slf4j
public class SessionCloseHandler extends AbstractHandler {

    @Override
    public Response processRequest() {
        log.info("SessionClose request process is starting...");
        SessionCloseRS sessionCloseRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getSessionCloseRQ());

            sessionCloseRS = (SessionCloseRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            if (!APPROVED.equals(sessionCloseRS.getStatus())) {
                log.error("Status returned from the web service response is different than Approved.");
                return getFaultyResponse(sessionCloseRS.getStatus(),
                        messages.getProperty("session.close.disapproved"),
                        messages.getProperty("session.error.status")
                );
            }

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(), MessageHeader.class);

            if (!headerProperties.getConversationId().equals(messageHeader.getConversationId())) {
                log.error("SessionClose response returned a different ConversationId.");
                return getFaultyResponse(sessionCloseRS.getStatus(),
                        messages.getProperty(ERROR_DESC),
                        messages.getProperty("error.convId"));
            }

            if (!headerProperties.getCpaid().equals(messageHeader.getCPAId())) {
                log.error("SessionClose response returned a different CPAId.");
                return getFaultyResponse(sessionCloseRS.getStatus(),
                        messages.getProperty(ERROR_DESC),
                        messages.getProperty("error.cpaid"));
            }

        } catch (Exception e) {
            log.error("Exception while closing session: " + e);
            return getFaultyResponse(ERROR, messages.getProperty("session.error.close"),
                    messages.getProperty("error.general") + e.getMessage());
        }

        log.info("Successfully retrieved SessionCLose Response.");

        return getSuccessfulResponse(sessionCloseRS.getStatus(), messages.getProperty("session.close.success"),
                messages.getProperty("session.approved"));
    }

    private SoapMessage sendAndReceive(SessionCloseRQ sessionCloseRQ) {
        return webServiceTemplate.sendAndReceive(
                webServiceMessage -> {
                    SoapHeader soapHeader = ((SoapMessage) webServiceMessage).getSoapHeader();
                    webServiceTemplate.getMarshaller().marshal(messageHeaderRq.getMessageHeader(SESSION_CLOSE), soapHeader.getResult());
                    webServiceTemplate.getMarshaller().marshal(securityRq.getSecurityHeader(), soapHeader.getResult());
                    MarshallingUtils.marshal(webServiceTemplate.getMarshaller(), sessionCloseRQ, webServiceMessage);
                },
                webServiceMessage -> (SoapMessage) webServiceMessage
        );
    }

    private SessionCloseRQ getSessionCloseRQ() {
        SessionCloseRQ sessionCloseRQ = new SessionCloseRQ();
        SessionCloseRQ.POS pos = new SessionCloseRQ.POS();
        SessionCloseRQ.POS.Source source = new SessionCloseRQ.POS.Source();
        source.setPseudoCityCode(headerProperties.getCpaid());
        pos.setSource(source);
        sessionCloseRQ.setPOS(pos);
        return sessionCloseRQ;
    }
}
