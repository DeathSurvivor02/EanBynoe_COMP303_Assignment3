/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sellers")
public class Seller {

    @Id
    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    @NotBlank(message = "Seller name cannot be empty")
    private String sellerName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Business type cannot be empty")
    private String businessType; // Individual, Small business, or Registered company

    @NotBlank(message = "City cannot be empty")
    private String city;

    private Double rating = 5.0; // Customer rating of the seller

    public Seller() {}

    public Seller(Long sellerId, String sellerName, String email, String businessType, String city, Double rating) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.email = email;
        this.businessType = businessType;
        this.city = city;
        this.rating = rating;
    }

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
