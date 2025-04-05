package com.example.app_server.UserAccountCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByMrnId(String mrnId);

    User findByPhoneNumber(String phoneNumber);
    User findByEmail(String email);
    User findByPhoneNumberAndVerificationCode(String phoneNumber, String verificationCode);
    User findByEmailAndVerificationCode(String email, String verificationCode);
    @Query("SELECT u FROM User u WHERE u.phoneNumber LIKE %:phoneNumber%")
    User findBySimilarPhoneNumber(String phoneNumber);
}
