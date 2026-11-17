/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final AtomicLong idGenerator = new AtomicLong(200);

    public ProductService(ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found with ID: " + id)));
    }

    public Flux<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    public Flux<Product> getProductsBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    public Mono<Product> createProduct(Product product) {
        // Business Rule: Validate that seller ID must refer to an existing seller
        return sellerRepository.existsById(product.getSellerId())
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new BusinessValidationException(
                                "Cannot create product: Seller ID " + product.getSellerId() + " does not exist."));
                    }
                    if (product.getProductId() == null || product.getProductId() <= 0) {
                        product.setProductId(idGenerator.incrementAndGet());
                    }
                    if (product.getStockQuantity() == null) {
                        product.setStockQuantity(0);
                    }
                    product.setAvailability(product.getStockQuantity() > 0);
                    return productRepository.save(product);
                });
    }

    public Mono<Product> updateProduct(Long id, Product updated) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found with ID: " + id)))
                .flatMap(existing -> {
                    if (updated.getProductName() != null && !updated.getProductName().isBlank()) {
                        existing.setProductName(updated.getProductName());
                    }
                    if (updated.getCategory() != null && !updated.getCategory().isBlank()) {
                        existing.setCategory(updated.getCategory());
                    }
                    if (updated.getPrice() != null && updated.getPrice() > 0) {
                        existing.setPrice(updated.getPrice());
                    }
                    if (updated.getStockQuantity() != null) {
                        existing.setStockQuantity(updated.getStockQuantity());
                    }
                    if (updated.getAvailability() != null) {
                        existing.setAvailability(updated.getAvailability());
                    }
                    if (updated.getSellerId() != null) {
                        return sellerRepository.existsById(updated.getSellerId())
                                .flatMap(exists -> {
                                    if (!exists) {
                                        return Mono.error(new BusinessValidationException(
                                                "Cannot update product: Seller ID " + updated.getSellerId() + " does not exist."));
                                    }
                                    existing.setSellerId(updated.getSellerId());
                                    return productRepository.save(existing);
                                });
                    }
                    return productRepository.save(existing);
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found with ID: " + id)))
                .flatMap(productRepository::delete);
    }

    public Mono<Boolean> existsById(Long id) {
        return productRepository.existsById(id);
    }
}
