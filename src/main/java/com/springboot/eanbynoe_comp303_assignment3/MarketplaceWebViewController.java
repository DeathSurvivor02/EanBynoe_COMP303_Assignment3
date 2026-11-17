/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
public class MarketplaceWebViewController {

    private final SellerService sellerService;
    private final ProductService productService;
    private final CustomerOrderService orderService;

    public MarketplaceWebViewController(SellerService sellerService,
                                        ProductService productService,
                                        CustomerOrderService orderService) {
        this.sellerService = sellerService;
        this.productService = productService;
        this.orderService = orderService;
    }

    // Home dashboard view
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("sellers", sellerService.getAllSellers());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("orders", orderService.getAllOrders());
        return "index";
    }

    // Sellers web view
    @GetMapping("/sellers")
    public String sellersView(Model model) {
        model.addAttribute("sellers", sellerService.getAllSellers());
        model.addAttribute("newSeller", new Seller());
        return "sellers";
    }

    @PostMapping("/sellers/add")
    public Mono<String> addSeller(@ModelAttribute Seller seller) {
        return sellerService.createSeller(seller)
                .thenReturn("redirect:/sellers");
    }

    @GetMapping("/sellers/delete/{id}")
    public Mono<String> deleteSeller(@PathVariable("id") Long id) {
        return sellerService.deleteSeller(id)
                .thenReturn("redirect:/sellers");
    }

    // Products web view
    @GetMapping("/products")
    public String productsView(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("sellers", sellerService.getAllSellers());
        model.addAttribute("newProduct", new Product());
        return "products";
    }

    @PostMapping("/products/add")
    public Mono<String> addProduct(@ModelAttribute Product product) {
        return productService.createProduct(product)
                .thenReturn("redirect:/products");
    }

    @GetMapping("/products/delete/{id}")
    public Mono<String> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id)
                .thenReturn("redirect:/products");
    }

    // Orders web view
    @GetMapping("/orders")
    public String ordersView(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("newOrder", new CustomerOrder());
        return "orders";
    }

    @PostMapping("/orders/add")
    public Mono<String> addOrder(@ModelAttribute CustomerOrder order) {
        return orderService.createOrder(order)
                .thenReturn("redirect:/orders");
    }

    @GetMapping("/orders/cancel/{id}")
    public Mono<String> cancelOrder(@PathVariable("id") Long id) {
        CustomerOrder update = new CustomerOrder();
        update.setOrderStatus("Cancelled");
        return orderService.updateOrder(id, update)
                .thenReturn("redirect:/orders");
    }

    @GetMapping("/orders/delete/{id}")
    public Mono<String> deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id)
                .thenReturn("redirect:/orders");
    }
}
