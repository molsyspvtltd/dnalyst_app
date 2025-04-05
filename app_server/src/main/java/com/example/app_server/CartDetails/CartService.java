// File: src/main/java/com/example/app_server/CartDetails/CartService.java
package com.example.app_server.CartDetails;

import com.example.app_server.ProductDetails.Product;
import com.example.app_server.ProductDetails.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository; // Add this

    // Get all carts
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    // Get cart by ID
    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    // Create a new cart
    public Cart createCart(Cart cart) {
        Product product = productRepository.findById(cart.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.setProduct(product);
        return cartRepository.save(cart);
    }

    // Update an existing cart
    public Cart updateCart(Long id, Cart cartDetails) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found for this id :: " + id));

        cart.setUser(cartDetails.getUser());
        cart.setPrice(cartDetails.getPrice());

        Product product = productRepository.findById(cartDetails.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.setProduct(product);

        return cartRepository.save(cart);
    }

    // Delete a cart
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}
