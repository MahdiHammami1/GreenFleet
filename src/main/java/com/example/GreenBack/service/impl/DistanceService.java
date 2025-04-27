package com.example.GreenBack.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class DistanceService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://maps.gomaps.pro/maps/api/distancematrix/json";
    private static final String API_KEY = "AlzaSytgZBhhogk1Dlm7JWUNuNz-BlNvWD7-gOu";

    public DistanceService() {
        this.restTemplate = new RestTemplate();
    }

    public DistanceDurationResponse getDistanceAndDuration(String origin, String destination) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
                    .queryParam("origins", origin)
                    .queryParam("destinations", destination)
                    .queryParam("mode", "driving")
                    .queryParam("key", API_KEY)
                    .build()
                    .toUri();

            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);

            if (response == null || !"OK".equals(response.get("status"))) {
                return null;
            }

            List<Map<String, Object>> rows = (List<Map<String, Object>>) response.get("rows");
            if (rows.isEmpty()) return null;

            List<Map<String, Object>> elements = (List<Map<String, Object>>) rows.get(0).get("elements");
            if (elements.isEmpty()) return null;

            Map<String, Object> element = elements.get(0);
            if (!"OK".equals(element.get("status"))) return null;

            Map<String, Object> distance = (Map<String, Object>) element.get("distance");
            Map<String, Object> duration = (Map<String, Object>) element.get("duration");

            String distanceText = (String) distance.get("text");
            String durationText = (String) duration.get("text");

            return new DistanceDurationResponse(distanceText, durationText);

        } catch (Exception e) {
            System.out.println("Request failed: " + e.getMessage());
            return null;
        }
    }

    // Record — clean — no setters needed
    public record DistanceDurationResponse(String distanceText, String durationText) {

    }
}
