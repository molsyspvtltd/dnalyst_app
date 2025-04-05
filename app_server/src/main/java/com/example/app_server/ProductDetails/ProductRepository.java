// File: src/main/java/com/example/app_server/ProductDetails/ProductRepository.java
package com.example.app_server.ProductDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p.productId FROM Product p ORDER BY p.productId DESC LIMIT 1")
    String findLastProductId();
}
