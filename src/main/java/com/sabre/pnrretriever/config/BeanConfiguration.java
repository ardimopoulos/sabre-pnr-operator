package com.sabre.pnrretriever.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableScheduling
public class BeanConfiguration {

    @Value(value = "${webservices.path}")
    private String path;

    @Value(value = "${service.endpoint}")
    private String endpoint;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths(path + ".message_header",
                                   path + ".wsse",
                                   path + ".sessionCreate.sessionCreateRQ",
                                   path + ".sessionCreate.sessionCreateRS",
                                   path + ".sessionClose.sessionCloseRQ",
                                   path + ".sessionClose.sessionCloseRS",
                                   path + ".sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RQ",
                                   path + ".sabreCommandLLS1_8_1.sabreCommandLLS1_8_1RS");
        return marshaller;
    }

    @Bean
    WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        webServiceTemplate.setDefaultUri(endpoint);
        return webServiceTemplate;
    }

    @Bean
    public Properties messages() throws IOException {
        Properties messages = new Properties();
        messages.load(getClass().getClassLoader().getResourceAsStream("messages.properties"));
        return messages;
    }

}
