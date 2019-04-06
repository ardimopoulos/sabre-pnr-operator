package com.sabre.pnrretriever.interceptor;

import com.sabre.pnrretriever.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import javax.xml.soap.SOAPMessage;

@Slf4j
@Component
public class SoapClientInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        log.info("Request: " + Utilities.getSOAPMessageAsString((SOAPMessage) messageContext.getRequest()));
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        log.info("Response: " + Utilities.getSOAPMessageAsString((SOAPMessage) messageContext.getResponse()));
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
