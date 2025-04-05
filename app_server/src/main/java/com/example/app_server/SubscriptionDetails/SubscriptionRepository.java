package com.example.app_server.SubscriptionDetails;

import com.example.app_server.SubscriptionDetails.Subscription;
import com.example.app_server.UserAccountCreation.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    // Additional query methods if needed

    // Example: Find by user ID (if needed in the future)
    Optional<Subscription> findByUser(User user);
    Optional<Subscription> findTopByOrderByIdDesc();
    List<Subscription> findByUserAndStatus(User user, SubscriptionStatus status);

    @Query("SELECT s FROM Subscription s WHERE s.user.mrnId = :mrnId AND s.paid = true")
    Optional<Subscription> findByUserMrnIdAndPaidTrue(String mrnId);
}
