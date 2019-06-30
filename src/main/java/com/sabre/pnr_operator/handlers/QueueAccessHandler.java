package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.models.QueueLine;
import com.sabre.pnr_operator.models.QueueList;
import com.sabre.pnr_operator.responses.Response;
import com.sabre.pnr_operator.utils.ResponseHeaderValidator;
import com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RQ.QueueAccessRQ;
import com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RS.QueueAccessRS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.sabre.pnr_operator.constants.HandlerConstants.ERROR;
import static com.sabre.pnr_operator.enums.Action.QUEUE_ACCESS_LLS;
import static java.lang.Boolean.TRUE;

@Component
@Slf4j
public class QueueAccessHandler extends AbstractHandler {

    @Autowired
    private QueueList queueList;

    @Override
    public Response processRequest() {
        log.info("QueueAccessHandler request is being proceed...");
        QueueAccessRS queueAccessRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getQueueAccessRQ());

            queueAccessRS = (QueueAccessRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            ResponseHeaderValidator responseHeaderValidator = new ResponseHeaderValidator(headerProperties);

            if (responseHeaderValidator.containInvalidHeaders(soapResponse, securityRq, webServiceTemplate.getUnmarshaller())) {
                return getFaultyResponseBasedOnInvalidHeaders(responseHeaderValidator.getInvalidHeaderReasons());
            }

        } catch (Exception e) {
            log.error("Exception while processing the QueueAccessHandler: " + e.getMessage());
            return getFaultyResponse(ERROR, messages.getProperty("queue.access.error"), messages.getProperty("error.general") + e.getMessage());
        }

        log.info("Successfully retrieved QueueAccess Response.");

        ArrayList<QueueLine> queueLineList = new ArrayList<>();

        for (QueueAccessRS.Line line : queueAccessRS.getLine()) {
            QueueLine queueLine = new QueueLine(
                    LocalDateTime.parse(line.getDateTime()),
                    line.getPOS().getSource().getPseudoCityCode(),
                    line.getPOS().getSource().getAgentSine(),
                    line.getUniqueID().getID(),
                    Integer.parseInt(line.getNumber())
            );

            queueLineList.add(queueLine);
        }

        queueList.setQueueLineList(queueLineList);
        queueList.setQueueListParagraph(queueAccessRS.getParagraph().getText());

        return getSuccessfulResponse(messages.getProperty("queue.access.success"), queueAccessRS.getApplicationResults().getStatus().value());
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

    private QueueAccessRQ getQueueAccessRQ() {
        QueueAccessRQ queueAccessRQ = new QueueAccessRQ();
        QueueAccessRQ.QueueIdentifier queueIdentifier = new QueueAccessRQ.QueueIdentifier();
        queueIdentifier.setPseudoCityCode(headerProperties.getCpaid());
        queueIdentifier.setNumber("100");
        QueueAccessRQ.QueueIdentifier.List list = new QueueAccessRQ.QueueIdentifier.List();
        list.setInd(TRUE);
        queueIdentifier.setList(list);
        queueAccessRQ.setQueueIdentifier(queueIdentifier);

        return queueAccessRQ;
    }
}
