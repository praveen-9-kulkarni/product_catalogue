package com.learn.product_catalogue.product_catalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learn.product_catalogue.product_catalogue.model.Inventory;
import com.learn.product_catalogue.product_catalogue.model.Product;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    // Find all inventories for a specific product
    List<Inventory> findByProduct(Product product);
    
    // Find all inventories for a specific product ID
    List<Inventory> findByProductId(Long productId);
    
    // Calculate the sum of quantity for a product
    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM Inventory i WHERE i.product.id = :productId")
    Integer sumQuantityByProductId(@Param("productId") Long productId);
} 