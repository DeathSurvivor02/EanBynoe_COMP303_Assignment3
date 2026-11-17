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
public class SellerService {

    private final SellerRepository sellerRepository;
    private final AtomicLong idGenerator = new AtomicLong(100);

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Flux<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Mono<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Seller not found with ID: " + id)));
    }

    public Mono<Seller> createSeller(Seller seller) {
        if (seller.getSellerId() == null || seller.getSellerId() <= 0) {
            seller.setSellerId(idGenerator.incrementAndGet());
        }
        return sellerRepository.save(seller);
    }

    public Mono<Seller> updateSeller(Long id, Seller updated) {
        return sellerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Seller not found with ID: " + id)))
                .flatMap(existing -> {
                    if (updated.getSellerName() != null && !updated.getSellerName().isBlank()) {
                        existing.setSellerName(updated.getSellerName());
                    }
                    if (updated.getEmail() != null && !updated.getEmail().isBlank()) {
                        existing.setEmail(updated.getEmail());
                    }
                    if (updated.getBusinessType() != null && !updated.getBusinessType().isBlank()) {
                        existing.setBusinessType(updated.getBusinessType());
                    }
                    if (updated.getCity() != null && !updated.getCity().isBlank()) {
                        existing.setCity(updated.getCity());
                    }
                    if (updated.getRating() != null) {
                        existing.setRating(updated.getRating());
                    }
                    return sellerRepository.save(existing);
                });
    }

    public Mono<Void> deleteSeller(Long id) {
        return sellerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Seller not found with ID: " + id)))
                .flatMap(sellerRepository::delete);
    }

    public Mono<Boolean> existsById(Long id) {
        return sellerRepository.existsById(id);
    }
}
