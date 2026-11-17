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
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    // GET /api/sellers - retrieve all sellers
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    // GET /api/sellers/{id} - retrieve a seller by ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Seller> getSellerById(@PathVariable("id") Long id) {
        return sellerService.getSellerById(id);
    }

    // POST /api/sellers - create a seller
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Seller> createSeller(@Valid @RequestBody Seller seller) {
        return sellerService.createSeller(seller);
    }

    // PUT /api/sellers/{id} - update a seller
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Seller> updateSeller(@PathVariable("id") Long id, @RequestBody Seller seller) {
        return sellerService.updateSeller(id, seller);
    }

    // DELETE /api/sellers/{id} - delete a seller
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSeller(@PathVariable("id") Long id) {
        return sellerService.deleteSeller(id);
    }
}
