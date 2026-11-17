/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerOrderRepository extends ReactiveMongoRepository<CustomerOrder, Long> {

    // Custom query method to retrieve orders by status
    Flux<CustomerOrder> findByOrderStatusIgnoreCase(String orderStatus);

    // Custom query method to find orders by customer email
    Flux<CustomerOrder> findByCustomerEmailIgnoreCase(String customerEmail);

    // Custom query method to find orders by product ID
    Flux<CustomerOrder> findByProductId(Long productId);
}
