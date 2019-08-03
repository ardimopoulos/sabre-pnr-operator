package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.headers.message_header.MessageHeaderRq;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.pnr_operator.responses.Response;
import com.sabre.pnr_operator.utils.ResponseHeaderValidator;
import com.sabre.web_services.sessionClose.sessionCloseRQ.SessionCloseRQ;
import com.sabre.web_services.sessionClose.sessionCloseRS.SessionCloseRS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import java.util.Properties;

import static com.sabre.pnr_operator.constants.HandlerConstants.APPROVED;
import static com.sabre.pnr_operator.constants.HandlerConstants.ERROR;
import static com.sabre.pnr_operator.enums.Action.SESSION_CLOSE;

@Component
@Slf4j
public class SessionCloseHandler extends AbstractHandler {

    public SessionCloseHandler(WebServiceTemplate webServiceTemplate, HeaderProperties headerProperties, MessageHeaderRq messageHeaderRq,
                               SecurityHeaderRq securityRq, Properties messages) {

        super(webServiceTemplate, headerProperties, messageHeaderRq, securityRq, messages);
    }

    @Override
    public Response processRequest() {
        log.info("SessionClose request process is starting...");
        SessionCloseRS sessionCloseRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getSessionCloseRQ(), SESSION_CLOSE);

            sessionCloseRS = (SessionCloseRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            if (!APPROVED.equals(sessionCloseRS.getStatus())) {
                log.error("Status returned from the web service response is different than Approved.");
                return getFaultyResponse(sessionCloseRS.getStatus(),
                        messages.getProperty("session.close.disapproved"),
                        messages.getProperty("session.error.status")
                );
            }

            ResponseHeaderValidator responseHeaderValidator = new ResponseHeaderValidator(headerProperties);

            if (responseHeaderValidator.containInvalidHeaders(soapResponse, securityRq, webServiceTemplate.getUnmarshaller())) {
                return getFaultyResponseBasedOnInvalidHeaders(responseHeaderValidator.getInvalidHeaderReasons());
            }

        } catch (Exception e) {
            log.error("Exception while closing session: " + e);
            return getFaultyResponse(ERROR, messages.getProperty("session.error.close"),
                    messages.getProperty("error.general") + e.getMessage());
        }

        log.info("Successfully retrieved SessionCLose Response.");

        return getSuccessfulResponse(messages.getProperty("session.close.success"),
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
