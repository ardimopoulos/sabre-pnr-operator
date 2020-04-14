package com.sabre.pnr_operator.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {

    private ApiResponse results;

    public ResultController(ApiResponse results) {
        this.results = results;
    }

    @GetMapping(value = "results")
    public ResponseEntity<ApiResponse> getResults() {
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
