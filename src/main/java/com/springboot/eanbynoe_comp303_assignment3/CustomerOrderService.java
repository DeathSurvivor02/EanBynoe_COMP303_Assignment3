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

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AtomicLong idGenerator = new AtomicLong(300);

    public CustomerOrderService(CustomerOrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Flux<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<CustomerOrder> getOrderById(Long id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer order not found with ID: " + id)));
    }

    public Flux<CustomerOrder> getOrdersByStatus(String status) {
        return orderRepository.findByOrderStatusIgnoreCase(status);
    }

    public Flux<CustomerOrder> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmailIgnoreCase(email);
    }

    /**
     * Creates an order with strict business validation:
     * 1. Product must exist.
     * 2. Order quantity must not exceed available stock.
     * 3. Product stock is reduced upon successful creation.
     */
    public Mono<CustomerOrder> createOrder(CustomerOrder order) {
        return productRepository.findById(order.getProductId())
                .switchIfEmpty(Mono.error(new BusinessValidationException(
                        "Cannot place order: Product ID " + order.getProductId() + " does not exist.")))
                .flatMap(product -> {
                    if (order.getQuantity() == null || order.getQuantity() <= 0) {
                        return Mono.error(new BusinessValidationException("Order quantity must be greater than zero."));
                    }
                    if (product.getStockQuantity() < order.getQuantity()) {
                        return Mono.error(new BusinessValidationException(
                                "Insufficient stock: Requested quantity " + order.getQuantity() +
                                        " exceeds available stock (" + product.getStockQuantity() + ")."));
                    }

                    // Deduct stock
                    int updatedStock = product.getStockQuantity() - order.getQuantity();
                    product.setStockQuantity(updatedStock);

                    if (order.getOrderId() == null || order.getOrderId() <= 0) {
                        order.setOrderId(idGenerator.incrementAndGet());
                    }
                    if (order.getOrderTime() == null) {
                        order.setOrderTime(LocalDateTime.now());
                    }
                    if (order.getOrderStatus() == null || order.getOrderStatus().isBlank()) {
                        order.setOrderStatus("Pending");
                    }

                    return productRepository.save(product)
                            .then(orderRepository.save(order));
                });
    }

    /**
     * Updates an existing order. If status is transitioned to 'Cancelled', stock is restored (Innovation feature).
     */
    public Mono<CustomerOrder> updateOrder(Long id, CustomerOrder updated) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer order not found with ID: " + id)))
                .flatMap(existing -> {
                    String oldStatus = existing.getOrderStatus();
                    String newStatus = updated.getOrderStatus();

                    if (updated.getCustomerName() != null && !updated.getCustomerName().isBlank()) {
                        existing.setCustomerName(updated.getCustomerName());
                    }
                    if (updated.getCustomerEmail() != null && !updated.getCustomerEmail().isBlank()) {
                        existing.setCustomerEmail(updated.getCustomerEmail());
                    }
                    if (newStatus != null && !newStatus.isBlank()) {
                        existing.setOrderStatus(newStatus);
                    }

                    // Innovation Feature: Stock Restoration on Cancellation
                    if (!"Cancelled".equalsIgnoreCase(oldStatus) && "Cancelled".equalsIgnoreCase(newStatus)) {
                        return productRepository.findById(existing.getProductId())
                                .flatMap(prod -> {
                                    prod.setStockQuantity(prod.getStockQuantity() + existing.getQuantity());
                                    return productRepository.save(prod);
                                })
                                .then(orderRepository.save(existing));
                    }

                    return orderRepository.save(existing);
                });
    }

    /**
     * Deletes an order and restores product stock if order was active (Innovation feature).
     */
    public Mono<Void> deleteOrder(Long id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer order not found with ID: " + id)))
                .flatMap(order -> {
                    // Restore stock if not already cancelled
                    if (!"Cancelled".equalsIgnoreCase(order.getOrderStatus())) {
                        return productRepository.findById(order.getProductId())
                                .flatMap(prod -> {
                                    prod.setStockQuantity(prod.getStockQuantity() + order.getQuantity());
                                    return productRepository.save(prod);
                                })
                                .then(orderRepository.delete(order));
                    }
                    return orderRepository.delete(order);
                });
    }
}
