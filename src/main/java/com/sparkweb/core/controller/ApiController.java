package com.sparkweb.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparkweb.core.model.Times;
import com.sparkweb.core.services.ApiService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/Data")
public class ApiController {


    private final ApiService apiService;

    public ApiController(ApiService apiService){this.apiService = apiService;}

   @GetMapping("")
    public ResponseEntity<?> getData(
        @RequestParam Times function,
        @RequestParam String symbol,
        @RequestParam(required = false) String interval,
        @RequestParam(defaultValue = "compact") String outputsize,
        @RequestParam(defaultValue = "json") String datatype
    ) {
        try {
            return ResponseEntity.ok(
                apiService.getDataByFunction(function, symbol, interval, outputsize, datatype)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    
}
