package com.example.Datadog.P95;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/P95")
public class P95Controller {

    private P95LatencyService p95LatencyService;

    public P95Controller(P95LatencyService p95LatencyService)
    {
        this.p95LatencyService=p95LatencyService;
    }

    @GetMapping("/{serviceId}")
    public Map<String, Double> p95(@PathVariable Long serviceId) {
        double p95 = p95LatencyService.calculateP95(serviceId, 24);
        return Map.of("p95LatencyMs", p95);
    }


}
