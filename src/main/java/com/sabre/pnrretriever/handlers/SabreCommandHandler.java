package com.sabre.pnrretriever.handlers;

import com.sabre.pnrretriever.responses.Response;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RQ.SabreCommandLLSRQ;
import com.sabre.web_services.sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RS.SabreCommandLLSRS;
import com.sabre.web_services.wsse.Security;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import java.time.LocalDateTime;

import static com.sabre.pnrretriever.headers.message_header.Action.SABRE_COMMAND_LLS;

@Component
public class SabreCommandHandler extends AbstractHandler {

    private SoapMessage soapResponse;

    @Setter
    private String command;

    @Override
    public Response processRequest() {
        SabreCommandLLSRS sabreCommandLLSRS;

        try {
            soapResponse = sendAndReceive(getSabreCommandLLSRQ());

            sabreCommandLLSRS = (SabreCommandLLSRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(), MessageHeader.class);

            if (!headerProperties.getConversationId().equals(messageHeader.getConversationId())) {
                return getFaultyResponse("fail", messages.getProperty("error.desc"),
                        messages.getProperty("error.convId"));
            }

            if (!headerProperties.getCpaid().equals(messageHeader.getCPAId())) {
                return getFaultyResponse("fail", messages.getProperty("error.desc"),
                        messages.getProperty("error.cpaid"));
            }

            Security security = (Security) getHeaderElement(soapResponse.getSoapHeader(), Security.class);

            if (!securityRq.getToken().equals(security.getBinarySecurityToken())) {
                return getFaultyResponse("fail", messages.getProperty("error.desc"),
                        messages.getProperty("error.token"));
            }

        } catch (Exception e) {
            System.out.println("Exception while executing sabre command:" + e.getMessage());
            return getFaultyResponse("error", messages.getProperty("command.error") + ":" + command,
                    messages.getProperty("error.general") + e.getMessage());
        }

        return getSuccessfulResponse("success", messages.getProperty("command.success"),
                sabreCommandLLSRS.getResponse());
    }

    private SoapMessage sendAndReceive(SabreCommandLLSRQ sabreCommandLLSRQ) {
        return webServiceTemplate.sendAndReceive(
                webServiceMessage -> {
                    SoapHeader soapHeader = ((SoapMessage) webServiceMessage).getSoapHeader();
                    webServiceTemplate.getMarshaller().marshal(messageHeaderRq.getMessageHeader(SABRE_COMMAND_LLS), soapHeader.getResult());
                    webServiceTemplate.getMarshaller().marshal(securityRq.getSecurityHeader(), soapHeader.getResult());
                    MarshallingUtils.marshal(webServiceTemplate.getMarshaller(), sabreCommandLLSRQ, webServiceMessage);
                },
                webServiceMessage -> (SoapMessage) webServiceMessage
        );
    }

    private SabreCommandLLSRQ getSabreCommandLLSRQ() {
        SabreCommandLLSRQ sabreCommandLLSRQ = new SabreCommandLLSRQ();
        sabreCommandLLSRQ.setTimeStamp(String.valueOf(LocalDateTime.now()));
        SabreCommandLLSRQ.Request request = new SabreCommandLLSRQ.Request();
        request.setHostCommand(command);
        request.setOutput("SCREEN");
        request.setCDATA(true);
        sabreCommandLLSRQ.setRequest(request);

        return sabreCommandLLSRQ;
    }

}
