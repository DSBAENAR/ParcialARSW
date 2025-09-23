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

    @GetMapping("/intraday")
    public ResponseEntity<?> getDataIntraDay(@RequestParam String symbol, @RequestParam String interval) {
        try {
            return ResponseEntity.ok(apiService.getDataIntraDay(symbol, interval));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDataDaily(@RequestParam String symbol, @RequestParam String interval) {
        try {
            return ResponseEntity.ok(apiService.getDataDaily(symbol, interval));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/weekly")
    public ResponseEntity<?> getDataWeekly(@RequestParam String symbol, @RequestParam String interval) {
        try {
            return ResponseEntity.ok(apiService.getDataWeekly(symbol, interval));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/Monthly")
    public ResponseEntity<?> getDataMonthly(@RequestParam String symbol, @RequestParam String interval) {
        try {
            return ResponseEntity.ok(apiService.getDataMonthly(symbol, interval));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    
}
