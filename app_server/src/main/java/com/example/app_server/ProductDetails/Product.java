
package com.example.app_server.ProductDetails;

import com.example.app_server.CartDetails.Cart;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

import java.util.Set;

@Entity
public class Product {

    @Id
    private String productId; // Changed to String for custom ID format

    private String productName;
    private Double price;
    private String image;

    @OneToMany(mappedBy = "product")
    private Set<Cart> carts; // Change from UserProduct to Cart

    // Generate a unique ID before persisting a new Product
//    @PrePersist
//    private void generateProductId() {
//        if (this.productId == null) {
//            this.productId = generateUniqueProductId();
//        }
//    }
//
//    private String generateUniqueProductId() {
//        // Example logic to generate unique ID in the format "DNLPI00001"
//        // This should be replaced with actual logic to fetch the last ID from the database
//        String lastProductId = "DNLPI00000"; // Placeholder for example
//        int nextId = Integer.parseInt(lastProductId.substring(5)) + 1;
//        return String.format("DNLPI%05d", nextId);
//    }
    public void generateProductId(ProductRepository productRepository) {
        if (this.productId == null) {
            this.productId = generateUniqueProductId(productRepository);
        }
    }

    private String generateUniqueProductId(ProductRepository productRepository) {
        String lastProductId = productRepository.findLastProductId();
        if (lastProductId == null) {
            lastProductId = "DNLPI00000"; // Initial ID if no products exist
        }
        int nextId = Integer.parseInt(lastProductId.substring(5)) + 1;
        return String.format("DNLPI%05d", nextId);
    }


    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }
}
