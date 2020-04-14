package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.headers.message_header.MessageHeaderRq;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.pnr_operator.responses.Response;
import com.sabre.pnr_operator.utils.ResponseHeaderValidator;
import com.sabre.web_services.sessionClose.sessionCloseRQ.SessionCloseRQ;
import com.sabre.web_services.sessionClose.sessionCloseRS.SessionCloseRS;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import java.util.Properties;

import static com.sabre.pnr_operator.constants.HandlerConstants.*;
import static com.sabre.pnr_operator.enums.Action.SESSION_CLOSE;

@Component
public class SessionCloseHandler extends AbstractHandler {

    private ResponseHeaderValidator responseHeaderValidator;

    public SessionCloseHandler(WebServiceTemplate webServiceTemplate, HeaderProperties headerProperties, MessageHeaderRq messageHeaderRq,
                               SecurityHeaderRq securityRq, Properties messages, ResponseHeaderValidator responseHeaderValidator) {

        super(webServiceTemplate, headerProperties, messageHeaderRq, securityRq, messages);

        this.responseHeaderValidator = responseHeaderValidator;
        this.responseHeaderValidator.setHeaderProperties(headerProperties);
    }

    @Override
    public Response processRequest() {
        SessionCloseRS sessionCloseRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getSessionCloseRQ(), SESSION_CLOSE);

            sessionCloseRS = (SessionCloseRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            if (!APPROVED.equals(sessionCloseRS.getStatus())) {
                return getResponse(false, sessionCloseRS.getStatus(),
                        messages.getProperty("session.close.disapproved"),
                        messages.getProperty("session.error.status")
                );
            }

            if (responseHeaderValidator.containInvalidHeaders(soapResponse, securityRq, webServiceTemplate.getUnmarshaller())) {
                return getErrorResponse(FAIL, messages.getProperty(ERROR_DESC),
                        getMessageBasedOnInvalidHeaders(responseHeaderValidator.getInvalidHeaderReasons()));
            }

        } catch (Exception e) {
            return getErrorResponse( ERROR, messages.getProperty("session.error.close"),
                    messages.getProperty("error.general") + e.getMessage());
        }

        return getSuccessResponse(SUCCESS, messages.getProperty("session.close.success"),
                messages.getProperty("session.approved"));
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
