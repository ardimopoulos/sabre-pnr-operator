package com.sabre.pnr_operator.responses;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class Response {

    private boolean success;
    private String status;
    private String description;
    private String message;
    private LocalDateTime timestamp;
}
