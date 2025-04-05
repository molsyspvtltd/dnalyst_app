package com.example.app_server.UserAccountCreation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginStatusRepository extends JpaRepository<LoginStatus, Long> {
    List<LoginStatus> findByEmail(String email);

}
