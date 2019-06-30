package com.sabre.pnr_operator.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QueueLine {

    private LocalDateTime localDateTime;
    private String pseudoCityCode;
    private String agentSine;
    private String uniqueId;
    private int number;
}
