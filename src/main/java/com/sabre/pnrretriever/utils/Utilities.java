package com.sabre.pnrretriever.utils;

import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

public class Utilities {

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
