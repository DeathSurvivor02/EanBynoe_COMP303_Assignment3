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
@RequestMapping("/api/orders")
public class CustomerOrderController {

    private final CustomerOrderService orderService;

    public CustomerOrderController(CustomerOrderService orderService) {
        this.orderService = orderService;
    }

    // GET /api/orders - retrieve all customer orders
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CustomerOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    // GET /api/orders/{id} - retrieve order by ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerOrder> getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    // Custom query: GET /api/orders/status/{status}
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CustomerOrder> getOrdersByStatus(@PathVariable("status") String status) {
        return orderService.getOrdersByStatus(status);
    }

    // Custom query: GET /api/orders/customer/{email}
    @GetMapping(value = "/customer/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CustomerOrder> getOrdersByCustomerEmail(@PathVariable("email") String email) {
        return orderService.getOrdersByCustomerEmail(email);
    }

    // POST /api/orders - create a new order (validates stock, deducts quantity)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerOrder> createOrder(@Valid @RequestBody CustomerOrder order) {
        return orderService.createOrder(order);
    }

    // PUT /api/orders/{id} - update an order (restores stock if cancelled)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerOrder> updateOrder(@PathVariable("id") Long id, @RequestBody CustomerOrder order) {
        return orderService.updateOrder(id, order);
    }

    // DELETE /api/orders/{id} - delete an order (restores stock if active)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id);
    }
}
