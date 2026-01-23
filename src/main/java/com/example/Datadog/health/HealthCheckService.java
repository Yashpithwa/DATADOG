package com.example.Datadog.health;

import com.example.Datadog.service.MointoredService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthCheckService {

    private final RestTemplate restTemplate;

    public HealthCheckService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HealthCheckResult check(MointoredService service) {

        String healthUrl = service.getBaseUrl() + "/actuator/health";
        long startTime = System.currentTimeMillis();

        try {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(healthUrl, String.class);

            long timeTaken = System.currentTimeMillis() - startTime;

            if (response.getStatusCode().is2xxSuccessful()) {
                return new HealthCheckResult("UP", timeTaken, null);
            } else {
                return new HealthCheckResult(
                        "DOWN",
                        timeTaken,
                        "Non-200 response"
                );
            }

        } catch (Exception e) {
            long timeTaken = System.currentTimeMillis() - startTime;
            return new HealthCheckResult("DOWN", timeTaken, e.getMessage());
        }
    }
}
