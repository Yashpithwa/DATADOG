package com.example.Datadog.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final MointoringService mointoringService;

    public ServiceController(MointoringService mointoringService) {
        this.mointoringService = mointoringService;
    }

    // ADD SERVICE
    @PostMapping
    public MointoredService add(
            @RequestBody MointoredService req,
            Authentication auth
    ) {
        String email = auth.getName();
        return mointoringService.addService(req, email);
    }

    @GetMapping("/count")
    public int count(Authentication auth) {

        List<MointoredService> services =
                mointoringService.getMyServices(auth.getName());

        return services.size();
    }

    @GetMapping("/count_active")
    public int count_active(Authentication auth) {

        List<MointoredService> services =
                mointoringService.getMyServices(auth.getName());

        int c = 0;
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).isActive()) {
                c++;
            }
        }
        return c;
    }



    // LIST SERVICES
    @GetMapping
    public List<MointoredService> list(Authentication auth) {


        return mointoringService.getMyServices(auth.getName());
    }

    // DISABLE SERVICE
    @PutMapping("/{id}/disable")
    public String disable(
            @PathVariable Long id,
            Authentication auth
    ) {
        mointoringService.disabledService(id, auth.getName());
        return "SERVICE DISABLED";
    }

    // 🔥 DELETE SERVICE (NEW)
    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id,
            Authentication auth
    ) {
        mointoringService.deleteService(id, auth.getName());
        return "SERVICE DELETED";
    }
}
