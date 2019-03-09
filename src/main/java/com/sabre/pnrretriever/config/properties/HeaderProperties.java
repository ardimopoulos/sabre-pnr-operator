package com.sabre.pnrretriever.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:header.properties")
public class HeaderProperties {

    @Value(value = "${messageHeader.conversationId}")
    private String conversationId;

    @Value(value = "${messageHeader.messageId}")
    private String messageId;

    @Value(value = "${messageHeader.from.partyId}")
    private String fromPartyId;

    @Value(value = "${messageHeader.to.partyId}")
    private String toPartyId;

    @Value(value = "${messageHeader.cpaid}")
    private String cpaid;
}
