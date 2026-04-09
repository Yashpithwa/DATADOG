package com.example.Datadog.UpTime;

import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UptimeServiceTest {

    @Mock
    private HealthHistoryRepository repo;

    @InjectMocks
    private UptimeService service;

    // ✅ 1. Empty history
    @Test
    void shouldReturnZeroWhenNoHistory() {

        when(repo.findRecentHistory(anyLong(), any()))
                .thenReturn(List.of());

        double result = service.calculateUpTime(1L, 24);

        assertEquals(0, result);
    }

    // ✅ 2. All UP
    @Test
    void shouldReturn100WhenAllUp() {

        HealthHistory h1 = new HealthHistory();
        h1.setStatus("UP");

        HealthHistory h2 = new HealthHistory();
        h2.setStatus("UP");

        when(repo.findRecentHistory(anyLong(), any()))
                .thenReturn(List.of(h1, h2));

        double result = service.calculateUpTime(1L, 24);

        assertEquals(100, result);
    }

    // ✅ 3. All DOWN
    @Test
    void shouldReturnZeroWhenAllDown() {

        HealthHistory h1 = new HealthHistory();
        h1.setStatus("DOWN");

        HealthHistory h2 = new HealthHistory();
        h2.setStatus("DOWN");

        when(repo.findRecentHistory(anyLong(), any()))
                .thenReturn(List.of(h1, h2));

        double result = service.calculateUpTime(1L, 24);

        assertEquals(0, result);
    }

    // ✅ 4. Mixed case
    @Test
    void shouldCalculateCorrectUptime() {

        HealthHistory h1 = new HealthHistory();
        h1.setStatus("UP");

        HealthHistory h2 = new HealthHistory();
        h2.setStatus("DOWN");

        HealthHistory h3 = new HealthHistory();
        h3.setStatus("UP");

        when(repo.findRecentHistory(anyLong(), any()))
                .thenReturn(List.of(h1, h2, h3));

        double result = service.calculateUpTime(1L, 24);

        assertEquals(66, result); // 2/3 * 100
    }
}
