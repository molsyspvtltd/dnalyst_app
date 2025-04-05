// File: src/main/java/com/example/app_server/CartDetails/CartRepository.java
package com.example.app_server.CartDetails;

import com.example.app_server.ProductDetails.Product;
import com.example.app_server.SubscriptionDetails.Subscription;
import com.example.app_server.UserAccountCreation.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // You can add custom query methods here if necessary
    // Example: Find by user ID (if needed in the future)
    Optional<Cart> findByProduct(Product product);
}
