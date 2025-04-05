package com.example.app_server.Roles;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @GetMapping("/dashboard")
    public String staffDashboard() {
        return "Welcome to the Staff Dashboard!";
    }
}