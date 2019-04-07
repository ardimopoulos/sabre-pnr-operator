package com.sabre.pnrretriever.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

import static com.sabre.pnrretriever.utils.Utilities.getSOAPMessageAsString;

@Slf4j
public class SoapClientInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        log.info("Request: " + getSOAPMessageAsString((SoapMessage) messageContext.getRequest()));
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        log.info("Response: " + getSOAPMessageAsString((SoapMessage) messageContext.getResponse()));
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }
}
