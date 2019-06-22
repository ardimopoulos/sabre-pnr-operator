package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.responses.Response;
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
import static com.sabre.pnr_operator.headers.message_header.Action.SESSION_CREATE;

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

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(), MessageHeader.class);

            if (!headerProperties.getConversationId().equals(messageHeader.getConversationId())) {
                log.error("SessionCreate response returned a different ConversationId.");
                return getFaultyResponse(sessionCreateRS.getStatus(),
                        messages.getProperty(ERROR_DESC),
                        messages.getProperty("error.convId"));
            }

            if (!headerProperties.getCpaid().equals(messageHeader.getCPAId())) {
                log.error("SessionCreate response returned a different CPAId.");
                return getFaultyResponse(sessionCreateRS.getStatus(),
                        messages.getProperty(ERROR_DESC),
                        messages.getProperty("error.cpaid"));
            }

            Security securityHeader = (Security) getHeaderElement(soapResponse.getSoapHeader(), Security.class);
            securityRq.setToken(securityHeader.getBinarySecurityToken());

            if (securityRq.isTokenEmpty()) {
                log.error("Security header does not contain token.");
                return getFaultyResponse(FAIL, messages.getProperty("error.desc"),
                        messages.getProperty("error.token.empty"));
            }

        } catch (Exception e) {
            log.error("Exception while opening session: " + e);
            return getFaultyResponse(ERROR, messages.getProperty("session.error.open"),
                        messages.getProperty("error.general") + e.getMessage());
        }

        log.info("Successfully retrieved SessionCreate Response.");

        return getSuccessfulResponse(sessionCreateRS.getStatus(), messages.getProperty("session.open.success"),
                messages.getProperty("session.approved"));
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
