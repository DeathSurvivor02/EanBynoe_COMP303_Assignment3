/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products - retrieve all products
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET /api/products/{id} - retrieve a product by ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Product> getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    // Custom query: GET /api/products/category/{category}
    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> getProductsByCategory(@PathVariable("category") String category) {
        return productService.getProductsByCategory(category);
    }

    // Custom query: GET /api/products/seller/{sellerId}
    @GetMapping(value = "/seller/{sellerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> getProductsBySellerId(@PathVariable("sellerId") Long sellerId) {
        return productService.getProductsBySellerId(sellerId);
    }

    // POST /api/products - create a product
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    // PUT /api/products/{id} - update a product
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // DELETE /api/products/{id} - delete a product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
