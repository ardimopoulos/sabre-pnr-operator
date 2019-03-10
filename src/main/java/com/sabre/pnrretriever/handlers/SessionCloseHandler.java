package com.sabre.pnrretriever.handlers;

import com.sabre.pnrretriever.responses.Response;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.sessionClose.sessionCloseRQ.SessionCloseRQ;
import com.sabre.web_services.sessionClose.sessionCloseRS.SessionCloseRS;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import static com.sabre.pnrretriever.headers.message_header.Action.SESSION_CLOSE;

@Component
public class SessionCloseHandler extends AbstractHandler {

    private SoapMessage soapResponse;

    @Override
    public Response processRequest() {
        SessionCloseRS sessionCloseRS;

        try {
            soapResponse = sendAndReceive(getSessionCloseRQ());

            sessionCloseRS = (SessionCloseRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            if (!"Approved".equals(sessionCloseRS.getStatus())) {
                return getFaultyResponse(sessionCloseRS.getStatus(),
                        messages.getProperty("session.close.disapproved"),
                        messages.getProperty("session.error.status")
                );
            }

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(), MessageHeader.class);

            if (!headerProperties.getConversationId().equals(messageHeader.getConversationId())) {
                return getFaultyResponse(sessionCloseRS.getStatus(),
                        messages.getProperty("error.desc"),
                        messages.getProperty("error.convId"));
            }

            if (!headerProperties.getCpaid().equals(messageHeader.getCPAId())) {
                return getFaultyResponse(sessionCloseRS.getStatus(),
                        messages.getProperty("error.desc"),
                        messages.getProperty("error.cpaid"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return getFaultyResponse("error", messages.getProperty("session.error.close"),
                    messages.getProperty("error.general") + e.getMessage());
        }

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
