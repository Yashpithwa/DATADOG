package com.example.Datadog.history;
import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HealthHistoryController {

    private final HealthHistoryRepository healthHistoryRepository;

    public HealthHistoryController(HealthHistoryRepository healthHistoryRepository) {
        this.healthHistoryRepository = healthHistoryRepository;
    }

    @GetMapping("/{serviceId}")
    public List<HealthHistory> history(@PathVariable Long serviceId) {
        return healthHistoryRepository
                .findByService_IdOrderByCheckAtDesc(serviceId);
    }

}
