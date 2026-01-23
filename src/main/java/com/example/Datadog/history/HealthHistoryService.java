package com.example.Datadog.history;


import com.example.Datadog.health.HealthCheckResult;
import com.example.Datadog.service.MointoredService;
import org.springframework.stereotype.Service;

@Service
public class HealthHistoryService {

    private final HealthHistoryRepository healthHistoryRepository;

    public HealthHistoryService(HealthHistoryRepository healthHistoryRepository){
        this.healthHistoryRepository=healthHistoryRepository;
    }

    public void save(MointoredService mointoredService, HealthCheckResult healthCheckResult)
    {
        HealthHistory h = new HealthHistory();
        h.setService(mointoredService);
        h.setStatus(healthCheckResult.getStatus());
        h.setResponseTime(healthCheckResult.getResponseTime());


        healthHistoryRepository.save(h);
    }


}
