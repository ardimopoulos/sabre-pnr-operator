//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.29 at 09:25:36 PM EEST 
//


package com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RQ;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Navigation" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Direction" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Action" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="E"/>
 *                                 &lt;enumeration value="I"/>
 *                                 &lt;enumeration value="L"/>
 *                                 &lt;enumeration value="U"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="Minus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Plus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Action">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="E"/>
 *                       &lt;enumeration value="EL"/>
 *                       &lt;enumeration value="ER"/>
 *                       &lt;enumeration value="I"/>
 *                       &lt;enumeration value="IR"/>
 *                       &lt;enumeration value="QR"/>
 *                       &lt;enumeration value="QXE"/>
 *                       &lt;enumeration value="QXER"/>
 *                       &lt;enumeration value="QXI"/>
 *                       &lt;enumeration value="QXIR"/>
 *                       &lt;enumeration value="QXR"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="QueueIdentifier" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="List" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Ind" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="PseudoCityCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Number" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Selection" maxOccurs="8" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="Criteria" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Function" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="ReturnHostCommand" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="2.0.9" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "navigation",
    "queueIdentifier",
    "selection"
})
@XmlRootElement(name = "QueueAccessRQ")
public class QueueAccessRQ {

    @XmlElement(name = "Navigation")
    protected QueueAccessRQ.Navigation navigation;
    @XmlElement(name = "QueueIdentifier")
    protected QueueAccessRQ.QueueIdentifier queueIdentifier;
    @XmlElement(name = "Selection")
    protected java.util.List<QueueAccessRQ.Selection> selection;
    @XmlAttribute(name = "ReturnHostCommand")
    protected Boolean returnHostCommand;
    @XmlAttribute(name = "TimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp;
    @XmlAttribute(name = "Version", required = true)
    protected String version;

    /**
     * Gets the value of the navigation property.
     * 
     * @return
     *     possible object is
     *     {@link QueueAccessRQ.Navigation }
     *     
     */
    public QueueAccessRQ.Navigation getNavigation() {
        return navigation;
    }

    /**
     * Sets the value of the navigation property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueueAccessRQ.Navigation }
     *     
     */
    public void setNavigation(QueueAccessRQ.Navigation value) {
        this.navigation = value;
    }

    /**
     * Gets the value of the queueIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link QueueAccessRQ.QueueIdentifier }
     *     
     */
    public QueueAccessRQ.QueueIdentifier getQueueIdentifier() {
        return queueIdentifier;
    }

    /**
     * Sets the value of the queueIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueueAccessRQ.QueueIdentifier }
     *     
     */
    public void setQueueIdentifier(QueueAccessRQ.QueueIdentifier value) {
        this.queueIdentifier = value;
    }

    /**
     * Gets the value of the selection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueueAccessRQ.Selection }
     * 
     * 
     */
    public java.util.List<QueueAccessRQ.Selection> getSelection() {
        if (selection == null) {
            selection = new ArrayList<QueueAccessRQ.Selection>();
        }
        return this.selection;
    }

    /**
     * Gets the value of the returnHostCommand property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReturnHostCommand() {
        return returnHostCommand;
    }

    /**
     * Sets the value of the returnHostCommand property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnHostCommand(Boolean value) {
        this.returnHostCommand = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "2.0.9";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Direction" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="Action" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="E"/>
     *                       &lt;enumeration value="I"/>
     *                       &lt;enumeration value="L"/>
     *                       &lt;enumeration value="U"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="Minus" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Plus" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="Action">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="E"/>
     *             &lt;enumeration value="EL"/>
     *             &lt;enumeration value="ER"/>
     *             &lt;enumeration value="I"/>
     *             &lt;enumeration value="IR"/>
     *             &lt;enumeration value="QR"/>
     *             &lt;enumeration value="QXE"/>
     *             &lt;enumeration value="QXER"/>
     *             &lt;enumeration value="QXI"/>
     *             &lt;enumeration value="QXIR"/>
     *             &lt;enumeration value="QXR"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "direction"
    })
    public static class Navigation {

        @XmlElement(name = "Direction")
        protected QueueAccessRQ.Navigation.Direction direction;
        @XmlAttribute(name = "Action")
        protected String action;

        /**
         * Gets the value of the direction property.
         * 
         * @return
         *     possible object is
         *     {@link QueueAccessRQ.Navigation.Direction }
         *     
         */
        public QueueAccessRQ.Navigation.Direction getDirection() {
            return direction;
        }

        /**
         * Sets the value of the direction property.
         * 
         * @param value
         *     allowed object is
         *     {@link QueueAccessRQ.Navigation.Direction }
         *     
         */
        public void setDirection(QueueAccessRQ.Navigation.Direction value) {
            this.direction = value;
        }

        /**
         * Gets the value of the action property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAction() {
            return action;
        }

        /**
         * Sets the value of the action property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAction(String value) {
            this.action = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="Action" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="E"/>
         *             &lt;enumeration value="I"/>
         *             &lt;enumeration value="L"/>
         *             &lt;enumeration value="U"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="Minus" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Plus" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Direction {

            @XmlAttribute(name = "Action", required = true)
            protected String action;
            @XmlAttribute(name = "Minus")
            protected String minus;
            @XmlAttribute(name = "Plus")
            protected String plus;

            /**
             * Gets the value of the action property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAction() {
                return action;
            }

            /**
             * Sets the value of the action property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAction(String value) {
                this.action = value;
            }

            /**
             * Gets the value of the minus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMinus() {
                return minus;
            }

            /**
             * Sets the value of the minus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMinus(String value) {
                this.minus = value;
            }

            /**
             * Gets the value of the plus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPlus() {
                return plus;
            }

            /**
             * Sets the value of the plus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPlus(String value) {
                this.plus = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="List" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="Ind" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="PseudoCityCode" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Number" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "list"
    })
    public static class QueueIdentifier {

        @XmlElement(name = "List")
        protected QueueAccessRQ.QueueIdentifier.List list;
        @XmlAttribute(name = "PseudoCityCode")
        protected String pseudoCityCode;
        @XmlAttribute(name = "Name")
        protected String name;
        @XmlAttribute(name = "Number")
        protected String number;

        /**
         * Gets the value of the list property.
         * 
         * @return
         *     possible object is
         *     {@link QueueAccessRQ.QueueIdentifier.List }
         *     
         */
        public QueueAccessRQ.QueueIdentifier.List getList() {
            return list;
        }

        /**
         * Sets the value of the list property.
         * 
         * @param value
         *     allowed object is
         *     {@link QueueAccessRQ.QueueIdentifier.List }
         *     
         */
        public void setList(QueueAccessRQ.QueueIdentifier.List value) {
            this.list = value;
        }

        /**
         * Gets the value of the pseudoCityCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPseudoCityCode() {
            return pseudoCityCode;
        }

        /**
         * Sets the value of the pseudoCityCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPseudoCityCode(String value) {
            this.pseudoCityCode = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the number property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumber() {
            return number;
        }

        /**
         * Sets the value of the number property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumber(String value) {
            this.number = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="Ind" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class List {

            @XmlAttribute(name = "Ind", required = true)
            protected boolean ind;

            /**
             * Gets the value of the ind property.
             * 
             */
            public boolean isInd() {
                return ind;
            }

            /**
             * Sets the value of the ind property.
             * 
             */
            public void setInd(boolean value) {
                this.ind = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="Criteria" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Function" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Selection {

        @XmlAttribute(name = "Criteria", required = true)
        protected String criteria;
        @XmlAttribute(name = "Function", required = true)
        protected String function;

        /**
         * Gets the value of the criteria property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCriteria() {
            return criteria;
        }

        /**
         * Sets the value of the criteria property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCriteria(String value) {
            this.criteria = value;
        }

        /**
         * Gets the value of the function property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFunction() {
            return function;
        }

        /**
         * Sets the value of the function property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFunction(String value) {
            this.function = value;
        }

    }

}
