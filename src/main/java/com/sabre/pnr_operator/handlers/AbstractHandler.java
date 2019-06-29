package com.sabre.pnr_operator.handlers;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.headers.message_header.MessageHeaderRq;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.pnr_operator.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Properties;

public abstract class AbstractHandler extends WebServiceGatewaySupport implements Handler {

    @Autowired
    WebServiceTemplate webServiceTemplate;

    @Autowired
    HeaderProperties headerProperties;

    @Autowired
    MessageHeaderRq messageHeaderRq;

    @Autowired
    SecurityHeaderRq securityRq;

    @Autowired
    Properties messages;

    Object getHeaderElement(SoapHeader header, Class className) throws IOException {
        Object headerElement = null;

        Iterator<SoapHeaderElement> headerElements = header.examineAllHeaderElements();

        while (headerElements.hasNext()) {
            SoapHeaderElement soapHeaderElement = headerElements.next();
            if (soapHeaderElement.getName().toString().contains(className.getSimpleName())) {
                headerElement = webServiceTemplate.getUnmarshaller().unmarshal(soapHeaderElement.getSource());
            }
        }
        return headerElement;
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

    Response getSuccessfulResponse(String status, String description, String message) {
        return new Response()
                .setSuccess(true)
                .setStatus(status)
                .setDescription(description)
                .setMessage(message)
                .setTimestamp(LocalDateTime.now());
    }

}
