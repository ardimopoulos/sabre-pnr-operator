package com.sabre.pnr_operator.headers.message_header;

import com.sabre.pnr_operator.config.properties.HeaderProperties;
import com.sabre.pnr_operator.enums.Action;
import com.sabre.web_services.message_header.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Clock;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class MessageHeaderRqImpl implements MessageHeaderRq {

    private HeaderProperties headerProperties;

    @Override
    public MessageHeader getMessageHeader(Action action) throws DatatypeConfigurationException {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setConversationId(headerProperties.getConversationId());
        messageHeader.setCPAId(headerProperties.getCpaid());
        messageHeader.setAction(action.getValue());
        messageHeader.setFrom(getFromHeaderElement());
        messageHeader.setTo(getToHeaderElement());
        messageHeader.setMessageData(getMessageDataHeaderElement());

        Service service = new Service();
        service.setValue(action.getValue());
        service.setType("SabreXML");
        messageHeader.setService(service);

        return messageHeader;
    }

    @Override
    public From getFromHeaderElement() {
        From from = new From();
        PartyId partyId = new PartyId();
        partyId.setValue(headerProperties.getFromPartyId());
        from.getPartyId().add(partyId);
        return from;
    }

    @Override
    public To getToHeaderElement() {
        To to = new To();
        PartyId partyId = new PartyId();
        partyId.setValue(headerProperties.getToPartyId());
        to.getPartyId().add(partyId);
        return to;
    }

    @Override
    public MessageData getMessageDataHeaderElement() throws DatatypeConfigurationException {
        MessageData messageData = new MessageData();
        messageData.setMessageId(headerProperties.getMessageId());
        messageData.setTimestamp(Clock.systemUTC().instant().toString());

        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(LocalDateTime.now().toString());

        messageData.setTimeToLive(xmlGregorianCalendar);

        return messageData;
    }
}

