//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.24 at 11:40:56 PM EET 
//


package com.sabre.web_services.message_header;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for status.type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="status.type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="Reset"/>
 *     &lt;enumeration value="Continue"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "status.type", namespace = "http://www.ebxml.org/namespaces/messageHeader")
@XmlEnum
public enum StatusType {

    @XmlEnumValue("Reset")
    RESET("Reset"),
    @XmlEnumValue("Continue")
    CONTINUE("Continue");
    private final String value;

    StatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatusType fromValue(String v) {
        for (StatusType c: StatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}