package com.example.Datadog.UpTime;


import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;



@Service
public class UptimeService {

    private final HealthHistoryRepository healthHistoryRepository;

    public UptimeService(HealthHistoryRepository healthHistoryRepository){
        this.healthHistoryRepository=healthHistoryRepository;
    }


    public double calculateUpTime(Long serviceId,int hours){


        LocalDateTime form = LocalDateTime.now().minusHours(hours);

        List<HealthHistory> history =  healthHistoryRepository.findRecentHistory(serviceId,form);

        if(history.isEmpty()) return 0;

        long total = history.size();
        long upCount = history.stream()
                .filter(h->"UP".equals(h.getStatus()))
        .count();

        return (upCount * 100)/total;
    }
}
