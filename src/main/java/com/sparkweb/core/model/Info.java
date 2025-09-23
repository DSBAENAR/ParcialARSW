package com.sparkweb.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Info(
    @JsonProperty("1. open") float open,
    @JsonProperty("2. high") float high,
    @JsonProperty("3. low") float low,
    @JsonProperty("4. close") float close,
    @JsonProperty("5. volume") int volume
) {}