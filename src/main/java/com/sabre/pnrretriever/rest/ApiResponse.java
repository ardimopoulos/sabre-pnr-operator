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

//    @JsonProperty("session-create")
//    private Response sessionCreateResponse;
//
//    @JsonProperty("sabre-command-lls")
//    private Response sabreCommandResponse;
//
//    @JsonProperty("session-close")
//    private Response sessionCloseResponse;
//
//    @PostConstruct
//    void initializeResponse() {
//        sessionCloseResponse = new Response().setStatus("pending").setDescription("pending").setMessage("pending");
//        sessionCreateResponse = new Response().setStatus("pending").setDescription("pending").setMessage("pending");
//        sabreCommandResponse = new Response().setStatus("pending").setDescription("pending").setMessage("pending");
//    }
}
