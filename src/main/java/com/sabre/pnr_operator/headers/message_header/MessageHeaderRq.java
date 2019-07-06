package com.sabre.pnr_operator.headers.message_header;

import com.sabre.pnr_operator.enums.Action;
import com.sabre.web_services.message_header.From;
import com.sabre.web_services.message_header.MessageData;
import com.sabre.web_services.message_header.MessageHeader;
import com.sabre.web_services.message_header.To;

import javax.xml.datatype.DatatypeConfigurationException;

public interface MessageHeaderRq {

    MessageHeader getMessageHeader(Action action) throws DatatypeConfigurationException;

    From getFromHeaderElement();

    To getToHeaderElement();

    MessageData getMessageDataHeaderElement() throws DatatypeConfigurationException;
}
