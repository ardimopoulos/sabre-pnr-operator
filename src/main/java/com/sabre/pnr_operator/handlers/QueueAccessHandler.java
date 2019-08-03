package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.headers.message_header.MessageHeaderRq;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.pnr_operator.models.QueueLine;
import com.sabre.pnr_operator.models.QueueList;
import com.sabre.pnr_operator.responses.Response;
import com.sabre.pnr_operator.utils.ResponseHeaderValidator;
import com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RQ.QueueAccessRQ;
import com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RS.QueueAccessRS;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

import static com.sabre.pnr_operator.constants.HandlerConstants.*;
import static com.sabre.pnr_operator.enums.Action.QUEUE_ACCESS_LLS;
import static java.lang.Boolean.TRUE;

@Component
public class QueueAccessHandler extends AbstractHandler {

    private QueueList queueList;

    public QueueAccessHandler(WebServiceTemplate webServiceTemplate, HeaderProperties headerProperties,
                              MessageHeaderRq messageHeaderRq, SecurityHeaderRq securityRq,
                              Properties messages, QueueList queueList) {

        super(webServiceTemplate, headerProperties, messageHeaderRq, securityRq, messages);
        this.queueList = queueList;
    }

    @Override
    public Response processRequest() {
        QueueAccessRS queueAccessRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getQueueAccessRQ(), QUEUE_ACCESS_LLS);

            queueAccessRS = (QueueAccessRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            ResponseHeaderValidator responseHeaderValidator = new ResponseHeaderValidator(headerProperties);

            if (responseHeaderValidator.containInvalidHeaders(soapResponse, securityRq, webServiceTemplate.getUnmarshaller())) {
                return getErrorResponse(FAIL, messages.getProperty(ERROR_DESC),
                        getMessageBasedOnInvalidHeaders(responseHeaderValidator.getInvalidHeaderReasons()));
            }

        } catch (Exception e) {
            return getErrorResponse(ERROR, messages.getProperty("queue.access.error"),
                    messages.getProperty("error.general") + e.getMessage());
        }

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

        return getSuccessResponse(SUCCESS, messages.getProperty("queue.access.success"), queueAccessRS.getApplicationResults().getStatus().value());
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
