//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.29 at 09:28:35 PM EEST 
//


package com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RS;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RS package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ResultSummary_QNAME = new QName("http://services.sabre.com/STL_Header/v120", "ResultSummary");
    private final static QName _Results_QNAME = new QName("http://services.sabre.com/STL/v01", "Results");
    private final static QName _Service_QNAME = new QName("http://services.sabre.com/STL_Header/v120", "Service");
    private final static QName _Identification_QNAME = new QName("http://services.sabre.com/STL_Header/v120", "Identification");
    private final static QName _ApplicationResults_QNAME = new QName("http://services.sabre.com/STL/v01", "ApplicationResults");
    private final static QName _Security_QNAME = new QName("http://services.sabre.com/STL_Header/v120", "Security");
    private final static QName _ProblemInformation_QNAME = new QName("http://services.sabre.com/STL/v01", "ProblemInformation");
    private final static QName _STLPayload_QNAME = new QName("http://services.sabre.com/STL/v01", "STL_Payload");
    private final static QName _Diagnostics_QNAME = new QName("http://services.sabre.com/STL_Header/v120", "Diagnostics");
    private final static QName _Traces_QNAME = new QName("http://services.sabre.com/STL_Header/v120", "Traces");
    private final static QName _SabreHeader_QNAME = new QName("http://services.sabre.com/STL_Header/v120", "SabreHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sabre.web_services.queueAccessLLS2_0_9.queueAccessLLS2_0_9RS
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueueAccessRS }
     * 
     */
    public QueueAccessRS createQueueAccessRS() {
        return new QueueAccessRS();
    }

    /**
     * Create an instance of {@link QueueAccessRS.Line }
     * 
     */
    public QueueAccessRS.Line createQueueAccessRSLine() {
        return new QueueAccessRS.Line();
    }

    /**
     * Create an instance of {@link QueueAccessRS.Line.POS }
     * 
     */
    public QueueAccessRS.Line.POS createQueueAccessRSLinePOS() {
        return new QueueAccessRS.Line.POS();
    }

    /**
     * Create an instance of {@link ApplicationResults }
     * 
     */
    public ApplicationResults createApplicationResults() {
        return new ApplicationResults();
    }

    /**
     * Create an instance of {@link Results }
     * 
     */
    public Results createResults() {
        return new Results();
    }

    /**
     * Create an instance of {@link QueueAccessRS.Paragraph }
     * 
     */
    public QueueAccessRS.Paragraph createQueueAccessRSParagraph() {
        return new QueueAccessRS.Paragraph();
    }

    /**
     * Create an instance of {@link ProblemInformation }
     * 
     */
    public ProblemInformation createProblemInformation() {
        return new ProblemInformation();
    }

    /**
     * Create an instance of {@link STLPayload }
     * 
     */
    public STLPayload createSTLPayload() {
        return new STLPayload();
    }

    /**
     * Create an instance of {@link SystemSpecificResults }
     * 
     */
    public SystemSpecificResults createSystemSpecificResults() {
        return new SystemSpecificResults();
    }

    /**
     * Create an instance of {@link HostCommand }
     * 
     */
    public HostCommand createHostCommand() {
        return new HostCommand();
    }

    /**
     * Create an instance of {@link Diagnostics }
     * 
     */
    public Diagnostics createDiagnostics() {
        return new Diagnostics();
    }

    /**
     * Create an instance of {@link Traces }
     * 
     */
    public Traces createTraces() {
        return new Traces();
    }

    /**
     * Create an instance of {@link SabreHeader }
     * 
     */
    public SabreHeader createSabreHeader() {
        return new SabreHeader();
    }

    /**
     * Create an instance of {@link ResultSummary }
     * 
     */
    public ResultSummary createResultSummary() {
        return new ResultSummary();
    }

    /**
     * Create an instance of {@link Service }
     * 
     */
    public Service createService() {
        return new Service();
    }

    /**
     * Create an instance of {@link Identification }
     * 
     */
    public Identification createIdentification() {
        return new Identification();
    }

    /**
     * Create an instance of {@link Security }
     * 
     */
    public Security createSecurity() {
        return new Security();
    }

    /**
     * Create an instance of {@link MessageCondition }
     * 
     */
    public MessageCondition createMessageCondition() {
        return new MessageCondition();
    }

    /**
     * Create an instance of {@link TrackingID }
     * 
     */
    public TrackingID createTrackingID() {
        return new TrackingID();
    }

    /**
     * Create an instance of {@link ProblemSummary }
     * 
     */
    public ProblemSummary createProblemSummary() {
        return new ProblemSummary();
    }

    /**
     * Create an instance of {@link TraceRecord }
     * 
     */
    public TraceRecord createTraceRecord() {
        return new TraceRecord();
    }

    /**
     * Create an instance of {@link ProblemBase }
     * 
     */
    public ProblemBase createProblemBase() {
        return new ProblemBase();
    }

    /**
     * Create an instance of {@link DiagnosticResults }
     * 
     */
    public DiagnosticResults createDiagnosticResults() {
        return new DiagnosticResults();
    }

    /**
     * Create an instance of {@link IdentifierSystem }
     * 
     */
    public IdentifierSystem createIdentifierSystem() {
        return new IdentifierSystem();
    }

    /**
     * Create an instance of {@link QueueAccessRS.Line.UniqueID }
     * 
     */
    public QueueAccessRS.Line.UniqueID createQueueAccessRSLineUniqueID() {
        return new QueueAccessRS.Line.UniqueID();
    }

    /**
     * Create an instance of {@link QueueAccessRS.Line.POS.Source }
     * 
     */
    public QueueAccessRS.Line.POS.Source createQueueAccessRSLinePOSSource() {
        return new QueueAccessRS.Line.POS.Source();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL_Header/v120", name = "ResultSummary")
    public JAXBElement<ResultSummary> createResultSummary(ResultSummary value) {
        return new JAXBElement<ResultSummary>(_ResultSummary_QNAME, ResultSummary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Results }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL/v01", name = "Results")
    public JAXBElement<Results> createResults(Results value) {
        return new JAXBElement<Results>(_Results_QNAME, Results.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Service }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL_Header/v120", name = "Service")
    public JAXBElement<Service> createService(Service value) {
        return new JAXBElement<Service>(_Service_QNAME, Service.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Identification }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL_Header/v120", name = "Identification")
    public JAXBElement<Identification> createIdentification(Identification value) {
        return new JAXBElement<Identification>(_Identification_QNAME, Identification.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationResults }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL/v01", name = "ApplicationResults", substitutionHeadNamespace = "http://services.sabre.com/STL/v01", substitutionHeadName = "Results")
    public JAXBElement<ApplicationResults> createApplicationResults(ApplicationResults value) {
        return new JAXBElement<ApplicationResults>(_ApplicationResults_QNAME, ApplicationResults.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Security }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL_Header/v120", name = "Security")
    public JAXBElement<Security> createSecurity(Security value) {
        return new JAXBElement<Security>(_Security_QNAME, Security.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProblemInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL/v01", name = "ProblemInformation")
    public JAXBElement<ProblemInformation> createProblemInformation(ProblemInformation value) {
        return new JAXBElement<ProblemInformation>(_ProblemInformation_QNAME, ProblemInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link STLPayload }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL/v01", name = "STL_Payload")
    public JAXBElement<STLPayload> createSTLPayload(STLPayload value) {
        return new JAXBElement<STLPayload>(_STLPayload_QNAME, STLPayload.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Diagnostics }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL_Header/v120", name = "Diagnostics")
    public JAXBElement<Diagnostics> createDiagnostics(Diagnostics value) {
        return new JAXBElement<Diagnostics>(_Diagnostics_QNAME, Diagnostics.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Traces }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL_Header/v120", name = "Traces")
    public JAXBElement<Traces> createTraces(Traces value) {
        return new JAXBElement<Traces>(_Traces_QNAME, Traces.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SabreHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sabre.com/STL_Header/v120", name = "SabreHeader")
    public JAXBElement<SabreHeader> createSabreHeader(SabreHeader value) {
        return new JAXBElement<SabreHeader>(_SabreHeader_QNAME, SabreHeader.class, null, value);
    }

}
