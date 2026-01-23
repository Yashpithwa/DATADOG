package com.example.Datadog.dashboard;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    public DashBoardController(DashBoardService dashBoardService)
    {
        this.dashBoardService=dashBoardService;
    }

    @GetMapping("/overview")
    public Map<String,Object> overview(Authentication auth){
        return dashBoardService.overview(auth.getName());
    }

    @GetMapping("/service")
    public List<Map<String,Object>> services(Authentication auth)
    {
        return dashBoardService.serviceStatus(auth.getName());
    }
}
