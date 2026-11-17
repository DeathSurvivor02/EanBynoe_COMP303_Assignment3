/**
 * Student Name: Ean Bynoe
 * Student Number: 301390261
 * Submission Date: November 17, 2026
 * Course: COMP303 - Java EE Programming
 * Assignment 3: Online Marketplace Management System
 */
package com.springboot.eanbynoe_comp303_assignment3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MarketplaceDataSeeder implements CommandLineRunner {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final CustomerOrderRepository orderRepository;

    public MarketplaceDataSeeder(SellerRepository sellerRepository,
                                 ProductRepository productRepository,
                                 CustomerOrderRepository orderRepository) {
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) {
        sellerRepository.count().subscribe(count -> {
            if (count == 0) {
                // Seed Sellers
                Seller s1 = new Seller(101L, "TechCorp Solutions", "contact@techcorp.com", "Registered company", "Toronto", 4.9);
                Seller s2 = new Seller(102L, "EcoGoods Studio", "support@ecogoods.ca", "Small business", "Vancouver", 4.7);
                Seller s3 = new Seller(103L, "Urban Apparel Co", "sales@urbanapparel.com", "Individual", "Montreal", 4.5);

                sellerRepository.save(s1).subscribe();
                sellerRepository.save(s2).subscribe();
                sellerRepository.save(s3).subscribe(savedS3 -> {
                    // Seed Products
                    Product p1 = new Product(201L, "Wireless Noise-Cancelling Headphones", "Electronics", 199.99, 25, true, 101L);
                    Product p2 = new Product(202L, "Ergonomic Mechanical Keyboard", "Electronics", 129.50, 40, true, 101L);
                    Product p3 = new Product(203L, "Organic Bamboo Cutlery Set", "Home and Kitchen", 24.99, 50, true, 102L);
                    Product p4 = new Product(204L, "Ceramic Pour-Over Coffee Dripper", "Home and Kitchen", 34.00, 15, true, 102L);
                    Product p5 = new Product(205L, "Classic Denim Jacket", "Clothing", 89.95, 30, true, 103L);

                    productRepository.save(p1).subscribe();
                    productRepository.save(p2).subscribe();
                    productRepository.save(p3).subscribe();
                    productRepository.save(p4).subscribe();
                    productRepository.save(p5).subscribe(savedP5 -> {
                        // Seed Orders
                        CustomerOrder o1 = new CustomerOrder(301L, "Alice Johnson", "alice@example.com", 201L, 2, "Confirmed", LocalDateTime.now().minusDays(2));
                        CustomerOrder o2 = new CustomerOrder(302L, "Bob Williams", "bob@example.com", 203L, 3, "Shipped", LocalDateTime.now().minusDays(1));
                        CustomerOrder o3 = new CustomerOrder(303L, "Charlie Brown", "charlie@example.com", 205L, 1, "Pending", LocalDateTime.now());

                        orderRepository.save(o1).subscribe();
                        orderRepository.save(o2).subscribe();
                        orderRepository.save(o3).subscribe(savedOrder -> {
                            System.out.println(">>> MarketplaceDataSeeder: Successfully seeded default Sellers, Products, and Orders.");
                        });
                    });
                });
            }
        });
    }
}
