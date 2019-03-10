package com.sabre.pnrretriever.handlers;

import com.sabre.pnrretriever.responses.Response;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.sessionCreate.sessionCreateRQ.SessionCreateRQ;
import com.sabre.web_services.sessionCreate.sessionCreateRS.SessionCreateRS;
import com.sabre.web_services.wsse.Security;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import static com.sabre.pnrretriever.headers.message_header.Action.SESSION_CREATE;

@Component
public class SessionCreateHandler extends AbstractHandler {

    private SoapMessage soapResponse;

    public Response processRequest() {
        SessionCreateRS sessionCreateRS;

        try {
            soapResponse = sendAndReceive(getSessionCreateRQ());

            sessionCreateRS = (SessionCreateRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            if (!"Approved".equals(sessionCreateRS.getStatus())) {
                return getFaultyResponse(sessionCreateRS.getStatus(),
                        messages.getProperty("session.create.disapproved"),
                        messages.getProperty("session.error.status")
                );
            }

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(), MessageHeader.class);

            if (!headerProperties.getConversationId().equals(messageHeader.getConversationId())) {
                return getFaultyResponse(sessionCreateRS.getStatus(),
                        messages.getProperty("error.desc"),
                        messages.getProperty("error.convId"));
            }

            if (!headerProperties.getCpaid().equals(messageHeader.getCPAId())) {
                return getFaultyResponse(sessionCreateRS.getStatus(),
                        messages.getProperty("error.desc"),
                        messages.getProperty("error.cpaid"));
            }

            Security securityHeader = (Security) getHeaderElement(soapResponse.getSoapHeader(), Security.class);
            securityRq.setToken(securityHeader.getBinarySecurityToken());

        } catch (Exception e) {
            System.out.println("Exception while opening session: " + e);
            return getFaultyResponse("error", messages.getProperty("session.error.open"),
                        messages.getProperty("error.general") + e.getMessage());
        }

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
