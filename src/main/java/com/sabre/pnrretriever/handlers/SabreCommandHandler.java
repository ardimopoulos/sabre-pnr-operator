package com.sabre.pnrretriever.handlers;

import com.sabre.pnrretriever.responses.Response;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RQ.SabreCommandLLSRQ;
import com.sabre.web_services.sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RS.SabreCommandLLSRS;
import com.sabre.web_services.wsse.Security;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import java.time.LocalDateTime;

import static com.sabre.pnrretriever.config.properties.ResultProperties.*;
import static com.sabre.pnrretriever.headers.message_header.Action.SABRE_COMMAND_LLS;

@Component
@Slf4j
public class SabreCommandHandler extends AbstractHandler {

    private SoapMessage soapResponse;

    @Setter
    private String command;

    @Override
    public Response processRequest() {
        log.info("SessionCommandLLS request is starting for command " + command + "...");
        SabreCommandLLSRS sabreCommandLLSRS;

        try {
            soapResponse = sendAndReceive(getSabreCommandLLSRQ());

            sabreCommandLLSRS = (SabreCommandLLSRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapResponse.getSoapHeader(), MessageHeader.class);

            if (!headerProperties.getConversationId().equals(messageHeader.getConversationId())) {
                log.error("SessionClose response returned a different ConversationId.");
                return getFaultyResponse(FAIL, messages.getProperty("error.desc"),
                        messages.getProperty("error.convId"));
            }

            if (!headerProperties.getCpaid().equals(messageHeader.getCPAId())) {
                log.error("SessionClose response returned a different CPAId.");
                return getFaultyResponse(FAIL, messages.getProperty("error.desc"),
                        messages.getProperty("error.cpaid"));
            }

            Security security = (Security) getHeaderElement(soapResponse.getSoapHeader(), Security.class);

            if (!securityRq.getToken().equals(security.getBinarySecurityToken())) {
                log.error("SessionClose response returned a different token");
                return getFaultyResponse(FAIL, messages.getProperty("error.desc"),
                        messages.getProperty("error.token"));
            }

        } catch (Exception e) {
            log.error("Exception while executing sabre command" + command + ":" + e.getMessage());
            return getFaultyResponse(ERROR, messages.getProperty("command.error") + ":" + command,
                    messages.getProperty("error.general") + e.getMessage());
        }

        log.info("Successfully retrieved SabreCommand (" + command + ") Response.");

        return getSuccessfulResponse(SUCCESS, messages.getProperty("command.success"),
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
