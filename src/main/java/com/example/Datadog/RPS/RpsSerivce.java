package com.example.Datadog.RPS;


import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RpsSerivce {

    private final HealthHistoryRepository healthHistoryRepository;

    public RpsSerivce(HealthHistoryRepository healthHistoryRepository)
    {
        this.healthHistoryRepository=healthHistoryRepository;
    }

    public double calculateRPS(long serviceId){

        LocalDateTime form = LocalDateTime.now().minusSeconds(60);
        List<HealthHistory> history = healthHistoryRepository.findRecentHistory(serviceId,form);

        if(history.isEmpty()) return 0;

        long total = history.size();

        return total/60.0;
    }
}
