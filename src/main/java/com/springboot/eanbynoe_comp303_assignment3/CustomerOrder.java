/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "customer_orders")
public class CustomerOrder {

    @Id
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotBlank(message = "Customer name cannot be empty")
    private String customerName;

    @NotBlank(message = "Customer email cannot be empty")
    @Email(message = "Please provide a valid customer email")
    private String customerEmail;

    @NotNull(message = "Product ID must refer to an existing product")
    private Long productId;

    @NotNull(message = "Order quantity is required")
    @Min(value = 1, message = "Order quantity must be greater than zero")
    private Integer quantity;

    @NotBlank(message = "Order status is required")
    @Pattern(regexp = "^(Pending|Confirmed|Shipped|Delivered|Cancelled)$",
             message = "Order status must be Pending, Confirmed, Shipped, Delivered, or Cancelled")
    private String orderStatus = "Pending";

    private LocalDateTime orderTime = LocalDateTime.now();

    public CustomerOrder() {}

    public CustomerOrder(Long orderId, String customerName, String customerEmail, Long productId, Integer quantity, String orderStatus, LocalDateTime orderTime) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.productId = productId;
        this.quantity = quantity;
        this.orderStatus = orderStatus != null ? orderStatus : "Pending";
        this.orderTime = orderTime != null ? orderTime : LocalDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }
}
