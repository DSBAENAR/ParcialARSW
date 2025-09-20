package com.sparkweb.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Info(float open, float high, float close, int volume) {

}
