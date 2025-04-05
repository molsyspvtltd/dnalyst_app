package com.example.app_server.dietphysio.service;

import com.example.app_server.dietphysio.model.AdminRole;
import com.example.app_server.dietphysio.repository.AdminRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminRoleService {
    @Autowired
    private AdminRoleRepository adminRoleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(AdminRole adminRole) {
        Optional<AdminRole> existingUser = adminRoleRepository.findByEmail(adminRole.getEmail());
        if (existingUser.isPresent()) {
            return "User already registered.";
        }
        adminRole.setPassword(passwordEncoder.encode(adminRole.getPassword()));
        adminRoleRepository.save(adminRole);
        return "Registration successful!";
    }

    public String loginUser(String email, String password) {
        Optional<AdminRole> existingUser = adminRoleRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            if (passwordEncoder.matches(password, existingUser.get().getPassword())) {
                return "Login successful!";
            }
            return "Invalid credentials.";
        }
        return "User not found.";
    }
}
