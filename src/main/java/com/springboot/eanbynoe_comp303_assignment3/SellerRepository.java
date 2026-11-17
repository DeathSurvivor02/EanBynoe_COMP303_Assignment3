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

@Repository
public interface SellerRepository extends ReactiveMongoRepository<Seller, Long> {
}
