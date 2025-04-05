package com.example.app_server.dietphysio.controller;

import com.example.app_server.dietphysio.model.AdminRole;
import com.example.app_server.dietphysio.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminRoleController {
    @Autowired
    private AdminRoleService adminRoleService;

    @PostMapping("/register")
    public String registerUser(@RequestBody AdminRole adminRole) {
        return adminRoleService.registerUser(adminRole);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password) {
        return adminRoleService.loginUser(email, password);
    }
}
