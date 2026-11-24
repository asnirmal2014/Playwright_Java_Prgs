package com.pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherReport 
{
    public static void main(String[] args) 
    {
        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=52.52&longitude=13.41"
                + "&hourly=temperature_2m";

        HttpClient client = HttpClient.newHttpClient();
       
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try 
        {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) 
            {
                System.err.println("API request failed. HTTP status: " + response.statusCode());
                return;
            }

            JsonNode root = new ObjectMapper().readTree(response.body());
            JsonNode hourly = root.path("hourly");
            JsonNode times = hourly.path("time");
            JsonNode temperatures = hourly.path("temperature_2m");
            String unit = root.path("hourly_units").path("temperature_2m").asText("°C");

            System.out.println("Hourly Weather Report — Berlin");
            System.out.println("--------------------------------");

            for (int i = 0; i < times.size(); i++) 
            {
                System.out.printf("%s: %.1f %s%n",
                        times.get(i).asText(),
                        temperatures.get(i).asDouble(),
                        unit);
            }
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
            System.err.println("Request was interrupted.");
        } 
        catch (Exception e) 
        {
            System.err.println("Could not retrieve weather: " + e.getMessage());
        }
    }
}
