package com.sabre.pnrretriever.headers.message_header;

import lombok.Getter;

public enum Action {

    SESSION_CREATE("SessionCreateRQ"),
    SESSION_CLOSE("SessionCloseRQ"),
    SABRE_COMMAND_LLS("SabreCommandLLSRQ");

    @Getter
    private String value;

    Action(String value) {
        this.value = value;
    }
}
