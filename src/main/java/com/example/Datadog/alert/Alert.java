package com.example.Datadog.alert;

import com.example.Datadog.service.MointoredService;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private MointoredService service;

    private String type;
    private String status;
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime closedAt;

    // ===== GETTERS (IMPORTANT) =====

    public Long getId() {
        return id;
    }

    public MointoredService getService() {
        return service;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    // ===== SETTERS =====

    public void setMointoredService(MointoredService service) {
        this.service = service;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void close() {
        this.status = "CLOSED";
        this.closedAt = LocalDateTime.now();
    }
}
