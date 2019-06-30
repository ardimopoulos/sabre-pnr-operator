package com.sabre.pnr_operator.utils;

import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class Utilities {

    private Utilities(){}

    public static Object getHeaderElement(SoapHeader header, Unmarshaller unmarshaller, Class className) throws IOException {
        Object headerElement = null;

        Iterator<SoapHeaderElement> headerElements = header.examineAllHeaderElements();

        while (headerElements.hasNext()) {
            SoapHeaderElement soapHeaderElement = headerElements.next();
            if (soapHeaderElement.getName().toString().contains(className.getSimpleName())) {
                headerElement =unmarshaller.unmarshal(soapHeaderElement.getSource());
            }
        }

        return headerElement;
    }

    public static String getSOAPMessageAsString(SoapMessage soapMessage) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");

            Source sc = soapMessage.getEnvelope().getSource();

            ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(streamOut);
            transformer.transform(sc, result);

            return streamOut.toString();

        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
