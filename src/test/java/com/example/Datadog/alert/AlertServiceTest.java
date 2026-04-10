package com.example.Datadog.alert;

import com.example.Datadog.email.EmailService;
import com.example.Datadog.health.HealthCheckResult;
import com.example.Datadog.history.HealthHistory;
import com.example.Datadog.history.HealthHistoryRepository;
import com.example.Datadog.service.MointoredService;
import com.example.Datadog.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private HealthHistoryRepository historyRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AlertService alertService;

    // 🔧 Helper method
    private MointoredService createService() {
        MointoredService service = new MointoredService();
        service.setServiceName("Test Service");

        User user = new User();
        user.setEmail("test@mail.com");

        service.setUser(user);
        service.setActive(true);

        return service;
    }

    // 🔧 Helper history
    private HealthHistory down() {
        HealthHistory h = new HealthHistory();
        h.setStatus("DOWN");
        return h;
    }

    private HealthHistory up() {
        HealthHistory h = new HealthHistory();
        h.setStatus("UP");
        return h;
    }

    // ✅ 1. DOWN ALERT (2 consecutive DOWN)
    @Test
    void shouldOpenDownAlert() {

        MointoredService service = createService();
        service.setId(1L);

        when(historyRepository.findByService_IdOrderByCheckAtDesc(1L))
                .thenReturn(List.of(down(), down()));

        when(alertRepository.findByService_IdAndTypeAndStatus(1L, "DOWN", "OPEN"))
                .thenReturn(Optional.empty());

        alertService.evaluate(service, new HealthCheckResult("DOWN", 1000, null));

        verify(alertRepository).save(any(Alert.class));
        verify(emailService).send(any(), contains("DOWN"), any());
    }

    // ✅ 2. SLOW ALERT (>2000 ms)
    @Test
    void shouldOpenSlowAlert() {

        MointoredService service = createService();
        service.setId(1L);

        when(historyRepository.findByService_IdOrderByCheckAtDesc(1L))
                .thenReturn(List.of(up(), up()));

        when(alertRepository.findByService_IdAndTypeAndStatus(1L, "SLOW", "OPEN"))
                .thenReturn(Optional.empty());

        alertService.evaluate(service, new HealthCheckResult("UP", 3000, null));

        verify(alertRepository).save(any(Alert.class));
        verify(emailService).send(any(), contains("SLOW"), any());
    }

    // ✅ 3. RECOVERY (UP → close alerts)
    @Test
    void shouldCloseAlertsOnRecovery() {

        MointoredService service = createService();
        service.setId(1L);

        Alert alert = new Alert();
        alert.setStatus("OPEN");

        when(historyRepository.findByService_IdOrderByCheckAtDesc(1L))
                .thenReturn(List.of(up(), up()));

        when(alertRepository.findByService_IdAndTypeAndStatus(1L, "DOWN", "OPEN"))
                .thenReturn(Optional.of(alert));

        alertService.evaluate(service, new HealthCheckResult("UP", 500, null));

        verify(alertRepository).save(alert);
        verify(emailService).send(any(), contains("Recovered"), any());
    }

 
    @Test
    void shouldNotCreateDuplicateAlert() {

        MointoredService service = createService();
        service.setId(1L);

        Alert existing = new Alert();
        existing.setStatus("OPEN");

        when(historyRepository.findByService_IdOrderByCheckAtDesc(1L))
                .thenReturn(List.of(down(), down()));

        when(alertRepository.findByService_IdAndTypeAndStatus(1L, "DOWN", "OPEN"))
                .thenReturn(Optional.of(existing));

        alertService.evaluate(service, new HealthCheckResult("DOWN", 1000, null));

        verify(alertRepository, never()).save(any());
        verify(emailService, never()).send(any(), any(), any());
    }
}
