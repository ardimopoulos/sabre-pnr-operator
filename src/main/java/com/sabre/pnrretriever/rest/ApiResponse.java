package com.sabre.pnrretriever.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabre.pnrretriever.responses.Response;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Data
public class ApiResponse {

    @JsonProperty("scheduled-task")
    Map<String, Response> apiResponse;

    @PostConstruct
    private void intialize() {
        apiResponse = new LinkedHashMap<>();
    }
}
