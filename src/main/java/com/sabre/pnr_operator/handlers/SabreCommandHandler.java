package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.headers.message_header.MessageHeaderRq;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.pnr_operator.responses.Response;
import com.sabre.pnr_operator.utils.ResponseHeaderValidator;
import com.sabre.web_services.sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RQ.SabreCommandLLSRQ;
import com.sabre.web_services.sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RS.SabreCommandLLSRS;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import java.time.LocalDateTime;
import java.util.Properties;

import static com.sabre.pnr_operator.constants.HandlerConstants.ERROR;
import static com.sabre.pnr_operator.enums.Action.SABRE_COMMAND_LLS;

@Component
@Slf4j
public class SabreCommandHandler extends AbstractHandler {

    @Setter
    private String command;

    public SabreCommandHandler(WebServiceTemplate webServiceTemplate, HeaderProperties headerProperties, MessageHeaderRq messageHeaderRq,
                               SecurityHeaderRq securityRq, Properties messages) {

        super(webServiceTemplate, headerProperties, messageHeaderRq, securityRq, messages);
    }

    @Override
    public Response processRequest() {
        log.info("SessionCommandLLS request is starting for command " + command + "...");
        SabreCommandLLSRS sabreCommandLLSRS;

        try {
            SoapMessage soapResponse = sendAndReceive(getSabreCommandLLSRQ(), SABRE_COMMAND_LLS);

            sabreCommandLLSRS = (SabreCommandLLSRS) webServiceTemplate.getUnmarshaller()
                    .unmarshal(soapResponse.getPayloadSource());

            ResponseHeaderValidator responseHeaderValidator = new ResponseHeaderValidator(headerProperties);

            if (responseHeaderValidator.containInvalidHeaders(soapResponse, securityRq, webServiceTemplate.getUnmarshaller())) {
                return getFaultyResponseBasedOnInvalidHeaders(responseHeaderValidator.getInvalidHeaderReasons());
            }

        } catch (Exception e) {
            log.error("Exception while executing sabre command" + command + ":" + e.getMessage());
            return getFaultyResponse(ERROR, messages.getProperty("command.error") + ":" + command,
                    messages.getProperty("error.general") + e.getMessage());
        }

        log.info("Successfully retrieved SabreCommand (" + command + ") Response.");

        return getSuccessfulResponse(messages.getProperty("command.success"),
                sabreCommandLLSRS.getResponse());
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
