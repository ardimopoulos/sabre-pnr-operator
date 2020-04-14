package com.sabre.pnr_operator.utils;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.enums.FaultyElement;
import com.sabre.pnr_operator.headers.security_header.SecurityHeaderRq;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.wsse.Security;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sabre.pnr_operator.enums.FaultyElement.*;
import static com.sabre.pnr_operator.utils.Utilities.getHeaderElement;
import static java.util.Objects.isNull;

@Component
@Scope(value = "prototype")
@NoArgsConstructor
public class ResponseHeaderValidator {

    @Setter
    private HeaderProperties headerProperties;

    @Getter
    private List<FaultyElement> invalidHeaderReasons;

    @PostConstruct
    private void postConstructInitialize() {
        invalidHeaderReasons = new ArrayList<>();
    }


    public boolean containInvalidHeaders(SoapMessage soapMessage, SecurityHeaderRq securityHeaderRq, Unmarshaller unmarshaller) throws IOException {
        MessageHeader messageHeader = (MessageHeader) getHeaderElement(soapMessage.getSoapHeader(), unmarshaller, MessageHeader.class);
        Security security = (Security) getHeaderElement(soapMessage.getSoapHeader(), unmarshaller, Security.class);

        return containInvalidHeaders(messageHeader, security, securityHeaderRq);
    }

    public boolean containInvalidHeaders(MessageHeader messageHeader, Security security) {
        boolean isInvalid = containInvalidMessageHeader(messageHeader);

        if (isNull(security.getBinarySecurityToken()) || security.getBinarySecurityToken().isEmpty()) {
            invalidHeaderReasons.add(TOKEN);
            isInvalid = true;
        }

        return isInvalid;
    }

    private boolean containInvalidHeaders(MessageHeader messageHeader, Security security, SecurityHeaderRq securityRq) {
        boolean isInvalid = containInvalidMessageHeader(messageHeader);

        if (!securityRq.getToken().equals(security.getBinarySecurityToken())) {
            invalidHeaderReasons.add(TOKEN);
            isInvalid = true;
        }

        return isInvalid;
    }

    private boolean containInvalidMessageHeader(MessageHeader messageHeader) {
        boolean isInvalid = false;

        if (!headerProperties.getConversationId().equals(messageHeader.getConversationId())) {
            invalidHeaderReasons.add(CONVERSATION_ID);
            isInvalid = true;
        }

        if (!headerProperties.getCpaid().equals(messageHeader.getCPAId())) {
            invalidHeaderReasons.add(CPAID);
            isInvalid = true;
        }

        return isInvalid;
    }
}
