package com.example.Datadog.scheduler;

import com.example.Datadog.alert.AlertService;
import com.example.Datadog.health.HealthCheckResult;
import com.example.Datadog.health.HealthCheckService;
import com.example.Datadog.history.HealthHistoryService;
import com.example.Datadog.service.MointoredService;
import com.example.Datadog.service.ServiceRepository;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.logging.Logger;

@Component
public class HealthCheckScheduler {

    private final ServiceRepository serviceRepository;
    private final HealthCheckService healthCheckService;
    private final HealthHistoryService healthHistoryService;
    private final AlertService alertservice;





    public HealthCheckScheduler(ServiceRepository serviceRepository,
                                HealthCheckService healthCheckService,
                                HealthHistoryService healthHistoryService,
                                AlertService alertservice) {
        this.serviceRepository = serviceRepository;
        this.healthCheckService = healthCheckService;
        this.healthHistoryService=healthHistoryService;
        this.alertservice=alertservice;
    }

    @Scheduled(fixedRate = 30000)
    public void runHealthChecks() {

        System.out.println("Running scheduled health check :)");

        List<MointoredService> services = serviceRepository.findAll();

        for (MointoredService service : services) {

            if (!service.isActive()) {
                continue;
            }

            HealthCheckResult result = healthCheckService.check(service);
            healthHistoryService.save(service,result);

            alertservice.evaluate(service,result);

        }

    }

}
