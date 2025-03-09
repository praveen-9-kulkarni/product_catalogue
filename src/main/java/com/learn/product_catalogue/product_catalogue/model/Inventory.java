package com.learn.product_catalogue.product_catalogue.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "inventories")
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    private Integer quantity;
    
    // Default constructor
    public Inventory() {
    }
    
    // Constructor with parameters
    public Inventory(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
} 