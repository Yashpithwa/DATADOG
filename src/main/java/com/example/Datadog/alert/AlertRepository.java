package com.example.Datadog.alert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<Alert> findByService_IdAndStatus(Long serviceId, String status);
}
