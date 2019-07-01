package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.headers.message_header.MessageHeaderRq;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.pnr_operator.responses.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapFault;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static com.sabre.pnr_operator.constants.HandlerConstants.*;
import static com.sabre.pnr_operator.enums.FaultyElement.*;

@AllArgsConstructor
@Slf4j
public abstract class AbstractHandler extends WebServiceGatewaySupport implements Handler {

    WebServiceTemplate webServiceTemplate;
    HeaderProperties headerProperties;
    MessageHeaderRq messageHeaderRq;
    SecurityHeaderRq securityRq;
    Properties messages;

    Response getFaultyResponseBasedOnInvalidHeaders (List<Enum> invalidHeaderReasons) {
        StringBuilder errorMessageBuilder = new StringBuilder();

        if (invalidHeaderReasons.contains(CONVERSATION_ID)) {
            log.error("SessionClose response returned a different ConversationId.");
            errorMessageBuilder.append(messages.getProperty("error.cpaid")).append("\n");

        }

        if (invalidHeaderReasons.contains(CPAID)) {
            log.error("SessionClose response returned a different CPAId.");
            errorMessageBuilder.append(messages.getProperty("error.cpaid")).append("\n");

        }

        if (invalidHeaderReasons.contains(TOKEN)) {
            log.error("SessionClose response returned a different token.");
            errorMessageBuilder.append(messages.getProperty("error.token"));
        }

        return getFaultyResponse(FAIL, messages.getProperty(ERROR_DESC), errorMessageBuilder.toString());
    }

    Response getFaultyResponse(String status, String description, String errorMessage) {
        return new Response()
                .setSuccess(false)
                .setStatus(status)
                .setDescription(description)
                .setMessage(errorMessage)
                .setTimestamp(LocalDateTime.now());
    }

    Response getFaultyResponse(SoapFault faultResponse) {
        return getFaultyResponse(faultResponse.getFaultCode().getLocalPart(),
                faultResponse.getFaultStringOrReason(),
                faultResponse.addFaultDetail().getSource().toString()
        );
    }

    Response getSuccessfulResponse(String description, String message) {
        return new Response()
                .setSuccess(true)
                .setStatus(SUCCESS)
                .setDescription(description)
                .setMessage(message)
                .setTimestamp(LocalDateTime.now());
    }

}
