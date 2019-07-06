package com.sabre.pnr_operator.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

import static com.sabre.pnr_operator.utils.Utilities.getSOAPMessageAsString;

@Slf4j
public class SoapClientInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) {
        log.info("Request: " + getSOAPMessageAsString((SoapMessage) messageContext.getRequest()));
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) {
        log.info("Response: " + getSOAPMessageAsString((SoapMessage) messageContext.getResponse()));
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) {}
}
