package com.example.Datadog.dashboard;

import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import com.example.Datadog.service.MointoredService;
import com.example.Datadog.service.ServiceRepository;
import com.example.Datadog.user.User;
import com.example.Datadog.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashBoardService {

    private final ServiceRepository serviceRepository;
    private final HealthHistoryRepository healthHistoryRepository;
    private final UserRepository userRepository;

    public DashBoardService(ServiceRepository serviceRepository,
                            HealthHistoryRepository healthHistoryRepository,
                            UserRepository userRepository) {
        this.serviceRepository = serviceRepository;
        this.healthHistoryRepository = healthHistoryRepository;
        this.userRepository = userRepository;
    }

    // ================= OVERVIEW =================
    public Map<String, Object> overview(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<MointoredService> services =
                serviceRepository.findByUserId(user.getId());

        long total = services.size();
        long active = services.stream()
                .filter(MointoredService::isActive)
                .count();

        long up = 0;
        long down = 0;

        for (MointoredService service : services) {

            HealthHistory last =
                    healthHistoryRepository
                            .findTopByService_IdOrderByCheckAtDesc(service.getId());

            if (last != null && "UP".equalsIgnoreCase(last.getStatus())) {
                up++;
            } else {
                down++;
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("totalService", total);
        res.put("activeService", active);
        res.put("up", up);
        res.put("down", down);

        return res;
    }

    // ================= SERVICE STATUS =================
    public List<Map<String, Object>> serviceStatus(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<MointoredService> services =
                serviceRepository.findByUserId(user.getId());

        List<Map<String, Object>> list = new ArrayList<>();

        for (MointoredService s : services) {

            HealthHistory last =
                    healthHistoryRepository
                            .findTopByService_IdOrderByCheckAtDesc(s.getId());

            Double avg =
                    healthHistoryRepository
                            .findAverageResponseTime(s.getId());

            Map<String, Object> m = new HashMap<>();
            m.put("serviceId", s.getId());
            m.put("serviceName", s.getServiceName());
            m.put("active", s.isActive());
            m.put("status", last != null ? last.getStatus() : "UNKNOWN");
            m.put("avgResponseTime", avg != null ? avg.longValue() : 0);

            list.add(m);
        }

        return list;
    }
}
