package com.example.Datadog.service;

import com.example.Datadog.user.User;
import com.example.Datadog.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MointoringService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public MointoringService(
            ServiceRepository serviceRepository,
            UserRepository userRepository
    ) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    // ADD SERVICE
    public MointoredService addService(MointoredService service, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        service.setUser(user);
        return serviceRepository.save(service);
    }

    // GET USER SERVICES
    public List<MointoredService> getMyServices(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return serviceRepository.findByUserId(user.getId());
    }
    // TOTAL SERVICES FOR SPECIFIC USER


    // DISABLE SERVICE
    public void disabledService(Long id, String email) {

        MointoredService s = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!s.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Not allowed");
        }

        s.setActive(false);
        serviceRepository.save(s);
    }

    // 🔥 DELETE SERVICE (NEW)
    public void deleteService(Long id, String email) {

        MointoredService s = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!s.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Not allowed");
        }

        serviceRepository.delete(s);
    }
}
