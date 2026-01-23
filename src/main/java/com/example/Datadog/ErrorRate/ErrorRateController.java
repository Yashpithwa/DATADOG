package com.example.Datadog.ErrorRate;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/errorate")
public class ErrorRateController {

    private final ErrorRateService errorRateService;

    public ErrorRateController(ErrorRateService errorRateService)
    {
        this.errorRateService=errorRateService;
    }

    @GetMapping("/{serviceId}")
    public Map<String,String> errorRate(@PathVariable long serviceId){
        double errorRate = errorRateService.calculateErrorRate(serviceId,24);
        return Map.of("errorRate",String.format("%.2f%%",errorRate));
    }
}
