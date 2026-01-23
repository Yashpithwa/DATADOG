package com.example.Datadog.P95;

import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class P95LatencyService {

    private final HealthHistoryRepository healthHistoryRepository;

    public P95LatencyService(HealthHistoryRepository healthHistoryRepository) {
        this.healthHistoryRepository = healthHistoryRepository;
    }

    public double calculateP95(long serviceId, int hours) {

        // past N hours
        LocalDateTime from = LocalDateTime.now().minusHours(hours);

        List<HealthHistory> history =
                healthHistoryRepository.findRecentHistory(serviceId, from);

        if (history.isEmpty()) {
            return 0;
        }

        // ✅ extract responseTime only
        List<Long> responseTimes = history.stream()
                .map(HealthHistory::getResponseTime)
                .collect(Collectors.toList());

        // ✅ sort response times
        Collections.sort(responseTimes);

        // ✅ p95 index
        int index = (int) Math.ceil(0.95 * responseTimes.size()) - 1;

        return responseTimes.get(index);
    }
}
