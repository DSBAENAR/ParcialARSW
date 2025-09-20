package com.sparkweb.core.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sparkweb.core.Config.RestClientConfig;
import com.sparkweb.core.model.Response;

@Service
public class ApiService {

    @Autowired
    private RestClientConfig template;

    @Autowired 
    private RestTemplateBuilder builder;
    @Value("{$url}")
    private String url;



    public String getData(){
        Response response = template.restTemplate(builder).getForObject(url, Response.class);
        return response.toString();
    }



}
