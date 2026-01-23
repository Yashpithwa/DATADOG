package com.example.Datadog.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HealthHistoryRepository extends JpaRepository<HealthHistory, Long> {

    // ✅ Latest history for a service (auto LIMIT 1 handled by Spring)
    HealthHistory findTopByService_IdOrderByCheckAtDesc(Long serviceId);

    // ✅ Full history sorted by latest first
    List<HealthHistory> findByService_IdOrderByCheckAtDesc(Long serviceId);

    // ✅ Recent history from a given time
    @Query("""
        SELECT h FROM HealthHistory h
        WHERE h.service.id = :serviceId
        AND h.checkAt >= :from
        ORDER BY h.checkAt DESC
    """)
    List<HealthHistory> findRecentHistory(
            @Param("serviceId") Long serviceId,
            @Param("from") LocalDateTime from
    );

    // ✅ Average response time
    @Query("""
        SELECT AVG(h.responseTime)
        FROM HealthHistory h
        WHERE h.service.id = :serviceId
    """)
    Double findAverageResponseTime(@Param("serviceId") Long serviceId);


}
