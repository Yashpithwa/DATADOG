package com.example.Datadog.health;


import com.example.Datadog.service.MointoredService;
import com.example.Datadog.service.ServiceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    private final HealthCheckService healthCheckService;
    private final ServiceRepository serviceRepository;

    public HealthCheckController(HealthCheckService healthCheckService,ServiceRepository serviceRepository)
    {
        this.healthCheckService=healthCheckService;
        this.serviceRepository=serviceRepository;
    }

    @GetMapping("/{serviceId}")
    public HealthCheckResult check(@PathVariable Long serviceId){

        MointoredService service = serviceRepository.findById(serviceId)
                .orElseThrow(()-> new RuntimeException("Service not found"));

        return healthCheckService.check(service);
    }
}
