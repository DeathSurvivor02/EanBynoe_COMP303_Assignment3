/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Product name must be provided")
    private String productName;

    @NotBlank(message = "Category must be provided")
    private String category; // Electronics, Clothing, Books, Home and Kitchen

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Product price must be greater than zero")
    private Double price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private Boolean availability = true;

    @NotNull(message = "Seller ID must refer to an existing seller")
    private Long sellerId;

    public Product() {}

    public Product(Long productId, String productName, String category, Double price, Integer stockQuantity, Boolean availability, Long sellerId) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.availability = availability != null ? availability : (stockQuantity != null && stockQuantity > 0);
        this.sellerId = sellerId;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
        this.availability = (stockQuantity != null && stockQuantity > 0);
    }

    public Boolean getAvailability() { return availability; }
    public void setAvailability(Boolean availability) { this.availability = availability; }

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
}
