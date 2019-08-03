package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.enums.Action;
import com.sabre.pnr_operator.headers.message_header.MessageHeaderRq;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.pnr_operator.responses.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static com.sabre.pnr_operator.enums.FaultyElement.*;

@AllArgsConstructor
@Slf4j
public abstract class AbstractHandler extends WebServiceGatewaySupport implements Handler {

    WebServiceTemplate webServiceTemplate;
    HeaderProperties headerProperties;
    MessageHeaderRq messageHeaderRq;
    SecurityHeaderRq securityRq;
    Properties messages;

    public String getMessageBasedOnInvalidHeaders(List<Enum> invalidHeaderReasons) {
        StringBuilder errorMessageBuilder = new StringBuilder();

        if (invalidHeaderReasons.contains(CONVERSATION_ID)) {
            log.error("Response returned a different ConversationId.");
            errorMessageBuilder.append(messages.getProperty("error.cpaid")).append("\n");
        }

        if (invalidHeaderReasons.contains(CPAID)) {
            log.error("Response returned a different CPAId.");
            errorMessageBuilder.append(messages.getProperty("error.cpaid")).append("\n");
        }

        if (invalidHeaderReasons.contains(TOKEN)) {
            log.error("Response returned a different token.");
            errorMessageBuilder.append(messages.getProperty("error.token"));
        }

        return errorMessageBuilder.toString();
    }

    Response getResponse(boolean isSuccess, String status, String description, String message) {
        return new Response()
                .setSuccess(isSuccess)
                .setStatus(status)
                .setDescription(description)
                .setMessage(message)
                .setTimestamp(LocalDateTime.now());
    }

    Response getSuccessResponse(String status, String description, String message) {
        return getResponse(true, status, description, message);
    }

    Response getErrorResponse(String status, String description, String message) {
        return getResponse(false, status, description, message);
    }

    public <T> SoapMessage sendAndReceive(T t, Action action) {
        return webServiceTemplate.sendAndReceive(
                webServiceMessage -> {
                    SoapHeader soapHeader = ((SoapMessage) webServiceMessage).getSoapHeader();

                    try {
                        webServiceTemplate.getMarshaller().marshal(messageHeaderRq.getMessageHeader(action), soapHeader.getResult());
                    } catch (DatatypeConfigurationException ex) {
                        log.info("Caught DatatypeConfigurationException: " + ex.getMessage());
                    }

                    webServiceTemplate.getMarshaller().marshal(securityRq.getSecurityHeader(), soapHeader.getResult());
                    MarshallingUtils.marshal(webServiceTemplate.getMarshaller(), t, webServiceMessage);
                },
                webServiceMessage -> (SoapMessage) webServiceMessage
        );
    }
}
