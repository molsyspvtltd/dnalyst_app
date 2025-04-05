package com.example.app_server.ProductDetails;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;



    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create or Update a Product
    @Transactional
    public Product saveProduct(Product product) {
        product.generateProductId(productRepository);
        return productRepository.save(product);
    }

    // Read - Get a product by ID
    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    // Read - Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Delete a product by ID
    public void deleteProductById(String productId) {
        productRepository.deleteById(productId);
    }
}
