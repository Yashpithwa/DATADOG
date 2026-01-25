package com.example.Datadog.email;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mailtest")
public class MailTestController {

    private final EmailService emailService;

    public MailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public String testtm() {
        emailService.send(
                "yashdpithwa@gmail.com",
                "Test Mail",
                "Spring Boot mail working"
        );
        return "Mail Sent";
    }
}
