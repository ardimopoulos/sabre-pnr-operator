package com.sabre.pnr_operator.enums;

import lombok.Getter;

public enum Action {

    SESSION_CREATE("SessionCreateRQ"),
    SESSION_CLOSE("SessionCloseRQ"),
    SABRE_COMMAND_LLS("SabreCommandLLSRQ"),
    QUEUE_ACCESS_LLS("QueueAccessLLSRQ");

    @Getter
    private String value;

    Action(String value) {
        this.value = value;
    }
}
