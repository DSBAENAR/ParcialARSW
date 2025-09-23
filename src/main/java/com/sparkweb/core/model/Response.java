package com.sparkweb.core.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Response(
    @JsonProperty("Meta Data") Metadata metadata,
    @JsonAlias({
        "Time Series (1min)",
        "Time Series (5min)",
        "Time Series (15min)",
        "Time Series (30min)",
        "Time Series (60min)",
        "Time Series (Daily)",
        "Time Series (Daily Adjusted)",
        "Weekly Time Series",
        "Weekly Adjusted Time Series",
        "Monthly Time Series"
    }) Map<String, Info> timeSeries
) {}