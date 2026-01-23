package com.example.Datadog.RPS;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rps")
public class RpsController {

    private final RpsSerivce rpsService;

    public RpsController(RpsSerivce rpsService) {
        this.rpsService = rpsService;
    }

    @GetMapping("/{serviceId}")
    public Map<String, Double> rpsRate(@PathVariable long serviceId) {
        double rps = rpsService.calculateRPS(serviceId);
        return Map.of("rps", rps);
    }
}
