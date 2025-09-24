package com.sparkweb.core.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Metadata(
    @JsonProperty("1. Information") String information,
    @JsonProperty("2. Symbol") String symbol,
    @JsonProperty("3. Last Refreshed") String lastRefreshed,
    @JsonInclude(JsonInclude.Include.NON_NULL)  
    @JsonAlias({"4. Interval"}) String interval,
    @JsonAlias({"4. Output Size", "5. Output Size"}) String outputSize,
    @JsonAlias({"5. Time Zone", "6. Time Zone"}) String timeZone
) {}
