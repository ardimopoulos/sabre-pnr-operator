package com.sabre.pnr_operator.config;

import com.sabre.pnr_operator.aop.logging.LoggingAspect;
import com.sabre.pnr_operator.interceptor.SoapClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
public class BeanConfiguration {

    @Value(value = "${webservices.path}")
    private String path;

    @Value(value = "${service.endpoint}")
    private String endpoint;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths(
                path + ".message_header",
                path + ".wsse",
                path + ".sessionCreate.sessionCreateRQ",
                path + ".sessionCreate.sessionCreateRS",
                path + ".sessionClose.sessionCloseRQ",
                path + ".sessionClose.sessionCloseRS",
                path + ".sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RQ",
                path + ".sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RS",
                path + ".queueAccessLLS2_0_9.queueAccessLLS2_0_9RQ",
                path + ".queueAccessLLS2_0_9.queueAccessLLS2_0_9RS"
        );

        return marshaller;
    }

    @Bean
    WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        webServiceTemplate.setDefaultUri(endpoint);
        ClientInterceptor[] clientInterceptors = {new SoapClientInterceptor()};
        webServiceTemplate.setInterceptors(clientInterceptors);
        return webServiceTemplate;
    }

    @Bean
    public Properties messages() throws IOException {
        Properties messages = new Properties();
        messages.load(getClass().getClassLoader().getResourceAsStream("messages.properties"));
        return messages;
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
