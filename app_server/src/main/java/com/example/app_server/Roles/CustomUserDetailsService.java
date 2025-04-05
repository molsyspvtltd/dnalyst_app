package com.example.app_server.Roles;

import com.example.app_server.security.EncryptionUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RoleUserRepository roleuserRepository;

    public CustomUserDetailsService(RoleUserRepository roleuserRepository) {
        this.roleuserRepository = roleuserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Looking up user: " + email);

        RoleUser user = roleuserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        System.out.println("Stored Password (Encoded): " + user.getPassword());  // Debugging line

        String role = (user.getRole() != null) ? "ROLE_" + user.getRole().name() : "ROLE_USER";

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
