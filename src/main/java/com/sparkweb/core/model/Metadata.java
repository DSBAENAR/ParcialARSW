package com.sparkweb.core.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Metadata(String information, String symbol, LocalDateTime lastRefreshed, String interval, String outputSize, ZoneId timeZone) {

}
