package com.sabre.pnr_operator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabre.pnr_operator.responses.Response;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Data
public class ApiResponse {

    @JsonProperty("scheduled-task")
    Map<String, Response> scheduledTask;

    @PostConstruct
    private void initialize() {
        scheduledTask = new LinkedHashMap<>();
    }
}
