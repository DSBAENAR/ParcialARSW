package com.sparkweb.core.Client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    private static final String BASE_URL =
        "https://dataapi-ejgmged6c0fsb7g7.canadacentral-01.azurewebsites.net/api/Data/intraday?interval=15min&symbol=IBM";

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        int totalRequests = 20;
        Thread[] threads = new Thread[totalRequests];

        // Crear hilos
        for (int i = 0; i < totalRequests; i++) {
            int requestId = i + 1;
            threads[i] = new Thread(() -> {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(BASE_URL))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.printf(
                        "Request #%d | Status: %d | Response: %s%n",
                        requestId,
                        response.statusCode(),
                        response.body().substring(0, Math.min(200, response.body().length()))
                    );
                    System.out.println("\n");
                } catch (Exception e) {
                    System.out.printf("Request #%d | FAIL: %s%n", requestId, e.getMessage());
                }
            });
        }

        // Iniciar hilos
        for (Thread t : threads) {
            t.start();
        }

        // Esperar que todos terminen (join)
        for (Thread th : threads) {
            th.join();
        }

        System.out.println("=== Todas las requests terminaron ===");
    }
}
