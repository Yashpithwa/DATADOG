package com.example.Datadog.UpTime;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/uptime")
public class UptimeController {

    private final UptimeService uptimeService;

    public UptimeController(UptimeService uptimeService) {
        this.uptimeService = uptimeService;
    }

    @GetMapping("/{serviceId}")
    public Map<String, String> uptime(@PathVariable Long serviceId) {
        double uptime = uptimeService.calculateUpTime(serviceId, 24);
        return Map.of("uptime", String.format("%.2f%%", uptime));
    }

}
