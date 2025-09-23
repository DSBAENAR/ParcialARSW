package com.sparkweb.core.Client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static final String BASE_URL = "http://localhost:8080/api/Data/intraday?symbol=IBM&interval=5min";

    public static void main(String[] args) {
        int numRequests = 20;
        ExecutorService executor = Executors.newFixedThreadPool(10);

        HttpClient client = HttpClient.newHttpClient();

        for (int i = 1; i < numRequests; i++) {

            int requestId = i;

            executor.submit(
                () -> {
                    try {

                        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL))
                        .GET()
                        .build();

                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        System.out.println("Request #" + requestId + " | Status: " + response.statusCode());
                        System.out.println("Response: " + response.body().substring(0, Math.min(200, response.body().length())) + "...\n");
                        
                    } catch (Exception e) {
                        System.err.println("Request #" + requestId + " failed: " + e.getMessage());
                    }
                }
            );
            
        }

        executor.shutdown();
        
    }
    


}
