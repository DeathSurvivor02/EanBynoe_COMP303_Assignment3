/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
