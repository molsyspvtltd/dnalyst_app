package com.example.app_server.Roles;

import com.example.app_server.security.EncryptionUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RoleUserRepository roleuserRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, RoleUserRepository roleuserRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.roleuserRepository = roleuserRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody RoleUser roleUser) {
        Optional<RoleUser> existingUser = roleuserRepository.findByEmail(roleUser.getEmail());
        if (existingUser.isPresent()) {
            return "Error: Email already registered!";
        }

        // Ensure password is only encoded if it is not already
        if (!roleUser.getPassword().startsWith("$2a$")) {
            roleUser.setPassword(passwordEncoder.encode(roleUser.getPassword()));
        }

        if (roleUser.getRole() == null) {
            roleUser.setRole(Role.SUB_ADMIN);
        }

        roleuserRepository.save(roleUser);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate using email & password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            // Fetch user details
            RoleUser roleUser = roleuserRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Generate JWT Token
            return jwtUtil.generateToken(roleUser);
        } catch (BadCredentialsException e) {
            return "Error: Invalid email or password!";
        }
    }

}

class AuthRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}