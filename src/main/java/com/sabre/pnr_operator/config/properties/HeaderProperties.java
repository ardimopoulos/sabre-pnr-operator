package com.sabre.pnr_operator.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.beans.ConstructorProperties;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "header")
public class HeaderProperties {
    private String conversationId;
    private String messageId;
    private String fromPartyId;
    private String toPartyId;
    private String cpaid;
    private String username;
    private String password;
}
