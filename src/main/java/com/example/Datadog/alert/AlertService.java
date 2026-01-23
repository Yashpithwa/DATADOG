package com.example.Datadog.alert;

import com.example.Datadog.health.HealthCheckResult;
import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import com.example.Datadog.service.MointoredService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final HealthHistoryRepository healthHistoryRepository;

    public AlertService(AlertRepository alertRepository,
                        HealthHistoryRepository healthHistoryRepository) {
        this.alertRepository = alertRepository;
        this.healthHistoryRepository = healthHistoryRepository;
    }

    public void evaluate(MointoredService mointoredService,
                         HealthCheckResult healthCheckResult) {

        List<HealthHistory> recent =
                healthHistoryRepository
                        .findByService_IdOrderByCheckAtDesc(mointoredService.getId());

        // FIXED if condition
        if (recent.size() >= 2
                && "DOWN".equals(recent.get(0).getStatus())
                && "DOWN".equals(recent.get(1).getStatus())) {

            openAlertIfNotExists(
                    mointoredService,
                    "DOWN",
                    "Service is DOWN continuously"
            );
        }

        if (healthCheckResult.getResponseTime() > 2000) {
            openAlertIfNotExists(
                    mointoredService,
                    "SLOW",
                    "Service response is slow"
            );
        }

        if ("UP".equals(healthCheckResult.getStatus())) {
            closeAlertIfExists(mointoredService);
        }
    }

    private void openAlertIfNotExists(
            MointoredService mointoredService,
            String type,
            String message
    ) {
        alertRepository
                .findByService_IdAndStatus(mointoredService.getId(), "OPEN")
                .orElseGet(() -> {
                    Alert alert = new Alert();
                    alert.setMointoredService(mointoredService);
                    alert.setType(type);
                    alert.setStatus("OPEN");
                    alert.setMessage(message);
                    return alertRepository.save(alert);
                });
    }

    private void closeAlertIfExists(MointoredService mointoredService) {
        alertRepository
                .findByService_IdAndStatus(mointoredService.getId(), "OPEN")
                .ifPresent(alert -> {
                    alert.close();
                    alertRepository.save(alert);
                });
    }
}
