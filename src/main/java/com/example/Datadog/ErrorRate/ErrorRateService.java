package com.example.Datadog.ErrorRate;


import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ErrorRateService {

    private  final HealthHistoryRepository healthHistoryRepository;

    public ErrorRateService(HealthHistoryRepository healthHistoryRepository)
    {
        this.healthHistoryRepository=healthHistoryRepository;
    }

    public double calculateErrorRate (long serviceId,int hours){

        LocalDateTime form = LocalDateTime.now().minusHours(hours);
        List<HealthHistory> history =  healthHistoryRepository.findRecentHistory(serviceId,form);


        if(history.isEmpty()) return 0;

        long total = history.size();
        long DownCount = history.stream().filter(h->"DOWN".equals(h.getStatus()) ).count();

        return (DownCount*100)/total;
    }


}
