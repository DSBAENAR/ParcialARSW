package com.sparkweb.core.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sparkweb.core.model.Response;
import com.sparkweb.core.model.Times;

@Service
public class ApiService {

  
    private final RestTemplate restTemplate;

    
    @Value("${alphavantage.key}")
    private String apiKey;

    @Value("${alphavantage.url}")
    private String apiUrl;


    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    

    public Response getDataIntraDay(String symbol, String interval){

        if(symbol == null || interval == null){
            throw new IllegalArgumentException("The arguments cannot be null or could not be found");
        }

        String url = apiUrl + 
                     "?function=" + Times.TIME_SERIES_INTRADAY +
                     "&symbol=" + symbol +
                     "&interval=" + interval +
                     "&apikey=" + apiKey;

        Response response = getResponse(url);

        return response;
    }

    public String getDataDaily(String symbol, String interval){

        checkParameters(symbol, interval);

        String url = apiUrl + 
                     "?function=" + Times.TIME_SERIES_DAILY +
                     "&symbol=" + symbol +
                     "&interval=" + interval +
                     "&apikey=" + apiKey;

        
        Response response = getResponse(url);

        return response.toString();

    }



    public String getDataWeekly(String symbol, String interval){

        checkParameters(symbol, interval);

        String url = apiUrl + 
                     "?function=" + Times.TIME_SERIES_WEEKLY +
                     "&symbol=" + symbol +
                     "&interval=" + interval +
                     "&apikey=" + apiKey;

        
        Response response = getResponse(url);

        return response.toString();

    }

    public String getDataMonthly(String symbol, String interval){

        checkParameters(symbol, interval);

        String url = apiUrl + 
                     "?function=" + Times.TIME_SERIES_MONTHLY +
                     "&symbol=" + symbol +
                     "&interval=" + interval +
                     "&apikey=" + apiKey;

        
        Response response = getResponse(url);

        return response.toString();

    }

    private Response getResponse(String url) {
        Response response = restTemplate.getForObject(url, Response.class);

        if (response.metadata() == null || response.timeSeries() == null){
            throw new RuntimeException("The metadata response or time serie is null");
        }
        return response;
    }

    private void checkParameters(String symbol, String interval) {
        if(symbol == null || interval == null){
            throw new IllegalArgumentException("The arguments cannot be null or could not be found");
        }
    }
}
