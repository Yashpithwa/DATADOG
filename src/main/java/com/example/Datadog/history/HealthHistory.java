package com.example.Datadog.history;

import com.example.Datadog.service.MointoredService;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_history")
public class HealthHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ renamed to service
    @ManyToOne
    @JoinColumn(name = "service_id")
    private MointoredService service;

    private String status;
    private long responseTime;

    private LocalDateTime checkAt = LocalDateTime.now();

    // ✅ getter & setter matching your service code
    public MointoredService getService() {
        return service;
    }

    public void setService(MointoredService service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public LocalDateTime getCheckAt() {
        return checkAt;
    }
    public void setCheckAt(LocalDateTime checkAt) {
        this.checkAt = checkAt;
    }

}
