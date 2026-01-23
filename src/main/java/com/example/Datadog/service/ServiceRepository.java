package com.example.Datadog.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<MointoredService,Long> {

    List<MointoredService> findByUserId(Long userId);
}
