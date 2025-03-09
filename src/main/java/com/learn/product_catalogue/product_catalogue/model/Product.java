package com.learn.product_catalogue.product_catalogue.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
    name = "products",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uk_product_name")
    }
)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();
    
    // Helper method to add inventory
    public void addInventory(Inventory inventory) {
        inventories.add(inventory);
        inventory.setProduct(this);
    }
    
    // Helper method to remove inventory
    public void removeInventory(Inventory inventory) {
        inventories.remove(inventory);
        inventory.setProduct(null);
    }
}
