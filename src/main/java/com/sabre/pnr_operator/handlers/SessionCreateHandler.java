package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.headers.message_header.MessageHeaderRq;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.pnr_operator.responses.Response;
import com.sabre.pnr_operator.utils.ResponseHeaderValidator;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.sessionCreate.sessionCreateRQ.SessionCreateRQ;
import com.sabre.web_services.sessionCreate.sessionCreateRS.SessionCreateRS;
import com.sabre.web_services.wsse.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import java.util.Properties;

import static com.sabre.pnr_operator.constants.HandlerConstants.*;
import static com.sabre.pnr_operator.enums.Action.SESSION_CREATE;
import static com.sabre.pnr_operator.utils.Utilities.getHeaderElement;

@Component
@Slf4j
public class SessionCreateHandler extends AbstractHandler {

    private ResponseHeaderValidator responseHeaderValidator;

    public SessionCreateHandler(WebServiceTemplate webServiceTemplate, HeaderProperties headerProperties, MessageHeaderRq messageHeaderRq,
                                SecurityHeaderRq securityRq, Properties messages, ResponseHeaderValidator responseHeaderValidator) {

        super(webServiceTemplate, headerProperties, messageHeaderRq, securityRq, messages);

        this.responseHeaderValidator = responseHeaderValidator;
        this.responseHeaderValidator.setHeaderProperties(headerProperties);
    }

    public Response processRequest() {
        SessionCreateRS sessionCreateRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getSessionCreateRQ(), SESSION_CREATE);

            sessionCreateRS = (SessionCreateRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            if (!APPROVED.equals(sessionCreateRS.getStatus())) {
                return getResponse(false, sessionCreateRS.getStatus(),
                        messages.getProperty("session.create.disapproved"),
                        messages.getProperty("session.error.status")
                );
            }

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(),
                    webServiceTemplate.getUnmarshaller(), MessageHeader.class);

            Security security = (Security) getHeaderElement(soapResponse.getSoapHeader(),
                    webServiceTemplate.getUnmarshaller(),  Security.class);

            if (responseHeaderValidator.containInvalidHeaders(messageHeader, security)) {
                return getErrorResponse(FAIL, messages.getProperty(ERROR_DESC),
                        getMessageBasedOnInvalidHeaders(responseHeaderValidator.getInvalidHeaderReasons()));
            }

            securityRq.setToken(security.getBinarySecurityToken());

        } catch (Exception e) {
            return getErrorResponse(ERROR, messages.getProperty("session.error.open"),
                        messages.getProperty("error.general") + e.getMessage());
        }

        return getSuccessResponse(SUCCESS, messages.getProperty("session.open.success"), messages.getProperty("session.approved"));
    }

    private SessionCreateRQ getSessionCreateRQ() {
        SessionCreateRQ sessionCreateRQ = new SessionCreateRQ();
        SessionCreateRQ.POS pos = new SessionCreateRQ.POS();
        SessionCreateRQ.POS.Source source = new SessionCreateRQ.POS.Source();
        source.setPseudoCityCode(headerProperties.getCpaid());
        pos.setSource(source);
        sessionCreateRQ.setPOS(pos);
        return sessionCreateRQ;
    }
}
