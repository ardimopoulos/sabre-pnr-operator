package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.responses.Response;
import com.sabre.pnr_operator.utils.ResponseHeaderValidator;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.sessionCreate.sessionCreateRQ.SessionCreateRQ;
import com.sabre.web_services.sessionCreate.sessionCreateRS.SessionCreateRS;
import com.sabre.web_services.wsse.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import static com.sabre.pnr_operator.constants.HandlerConstants.*;
import static com.sabre.pnr_operator.enums.Action.SESSION_CREATE;
import static com.sabre.pnr_operator.utils.Utilities.getHeaderElement;

@Component
@Slf4j
public class SessionCreateHandler extends AbstractHandler {

    public Response processRequest() {
        log.info("SessionCreate request process is starting...");
        SessionCreateRS sessionCreateRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getSessionCreateRQ());

            sessionCreateRS = (SessionCreateRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            if (!APPROVED.equals(sessionCreateRS.getStatus())) {
                log.error("Status returned from the web service response is different than Approved.");
                return getFaultyResponse(sessionCreateRS.getStatus(),
                        messages.getProperty("session.create.disapproved"),
                        messages.getProperty("session.error.status")
                );
            }

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(),
                    webServiceTemplate.getUnmarshaller(), MessageHeader.class);

            Security security = (Security) getHeaderElement(soapResponse.getSoapHeader(),
                    webServiceTemplate.getUnmarshaller(),  Security.class);

            ResponseHeaderValidator responseHeaderValidator = new ResponseHeaderValidator(headerProperties);

            if (responseHeaderValidator.containInvalidHeaders(messageHeader, security)) {
                return getFaultyResponseBasedOnInvalidHeaders(responseHeaderValidator.getInvalidHeaderReasons());
            }

            securityRq.setToken(security.getBinarySecurityToken());

        } catch (Exception e) {
            log.error("Exception while opening session: " + e);
            return getFaultyResponse(ERROR, messages.getProperty("session.error.open"),
                        messages.getProperty("error.general") + e.getMessage());
        }

        log.info("Successfully retrieved SessionCreate Response.");

        return getSuccessfulResponse(messages.getProperty("session.open.success"), messages.getProperty("session.approved"));
    }

    private SoapMessage sendAndReceive(SessionCreateRQ sessionCreateRQ) {
        return webServiceTemplate.sendAndReceive(
                webServiceMessage -> {
                    SoapHeader soapHeader = ((SoapMessage) webServiceMessage).getSoapHeader();
                    webServiceTemplate.getMarshaller().marshal(messageHeaderRq.getMessageHeader(SESSION_CREATE), soapHeader.getResult());
                    webServiceTemplate.getMarshaller().marshal(securityRq.getSessionSecurityHeader(), soapHeader.getResult());
                    MarshallingUtils.marshal(webServiceTemplate.getMarshaller(), sessionCreateRQ, webServiceMessage);
                },
                webServiceMessage -> (SoapMessage) webServiceMessage
        );
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
