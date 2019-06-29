//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.29 at 09:28:35 PM EEST 
//


package com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RS;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiagnosticLevels.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DiagnosticLevels">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Mock"/>
 *     &lt;enumeration value="Simulate"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DiagnosticLevels")
@XmlEnum
public enum DiagnosticLevels {


    /**
     * Return a sample message without invoking service logic.
     * 
     */
    @XmlEnumValue("Mock")
    MOCK("Mock"),

    /**
     * Compute response without making changes to service data, state or status.
     * 
     */
    @XmlEnumValue("Simulate")
    SIMULATE("Simulate");
    private final String value;

    DiagnosticLevels(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DiagnosticLevels fromValue(String v) {
        for (DiagnosticLevels c: DiagnosticLevels.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
