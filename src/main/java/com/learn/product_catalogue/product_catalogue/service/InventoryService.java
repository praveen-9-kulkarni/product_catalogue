package com.learn.product_catalogue.product_catalogue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.product_catalogue.product_catalogue.model.Inventory;
import com.learn.product_catalogue.product_catalogue.model.Product;
import com.learn.product_catalogue.product_catalogue.repository.InventoryRepository;
import com.learn.product_catalogue.product_catalogue.repository.ProductRepository;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    // Get total available inventory count for a product
    public Integer getTotalInventoryForProduct(Long productId) {
        // First ensure the product exists
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        
        List<Inventory> inventories = inventoryRepository.findByProductId(productId);
        
        // Count only inventories with quantity = 1
        return (int) inventories.stream()
                .filter(inv -> inv.getQuantity() == 1)
                .count();
    }
    
    // Add multiple inventories for a product
    @Transactional
    public void addInventories(Long productId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        // First ensure the product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        List<Inventory> newInventories = new ArrayList<>();
        
        // Create the specified number of inventory records, each with quantity=1
        for (int i = 0; i < quantity; i++) {
            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(1);
            newInventories.add(inventory);
        }
        
        // Save all new inventory records
        inventoryRepository.saveAll(newInventories);
    }
    
    // Remove multiple inventories for a product
    @Transactional
    public void removeInventories(Long productId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        // First ensure the product exists
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        
        // Find all available inventories for the product (where quantity=1)
        List<Inventory> availableInventories = inventoryRepository.findByProductId(productId).stream()
                .filter(inv -> inv.getQuantity() == 1)
                .collect(Collectors.toList());
        
        // Check if there are enough inventories to remove
        if (availableInventories.size() < quantity) {
            throw new IllegalStateException("Not enough inventory available. Requested: " + 
                    quantity + ", Available: " + availableInventories.size());
        }
        
        // Set quantity=0 for the specified number of inventories
        for (int i = 0; i < quantity; i++) {
            Inventory inventory = availableInventories.get(i);
            inventory.setQuantity(0);
        }
        
        // Save the updated inventories
        inventoryRepository.saveAll(availableInventories.subList(0, quantity));
    }
} 