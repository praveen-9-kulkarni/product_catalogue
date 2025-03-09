package com.learn.product_catalogue.product_catalogue.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.product_catalogue.dto.ProductCreateDTO;
import com.learn.product_catalogue.dto.ProductDTO;
import com.learn.product_catalogue.dto.ProductUpdateDTO;
import com.learn.product_catalogue.dto.mapper.ProductMapper;
import com.learn.product_catalogue.product_catalogue.model.Product;
import com.learn.product_catalogue.product_catalogue.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    @Autowired
    private InventoryService inventoryService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto);
    }
    
    /**
     * Check if a product exists by its ID
     * 
     * @param id the product ID to check
     * @return true if the product exists, false otherwise
     */
    public boolean productExists(Long id) {
        return productRepository.existsById(id);
    }

    public ProductDTO saveProduct(ProductCreateDTO createDto) {
        Product product = (Product) productMapper.toEntity(createDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }
    
    public ProductDTO updateProduct(Long id, ProductUpdateDTO updateDto) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    Product updatedProduct = (Product) productMapper.updateEntityFromDto(existingProduct, updateDto);
                    return productMapper.toDto(productRepository.save(updatedProduct));
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    // Get total available inventory count for a product
    public Integer getProductInventoryCount(Long productId) {
        return inventoryService.getTotalInventoryForProduct(productId);
    }
    
    // Add multiple inventories to a product
    @Transactional
    public void addInventoriesToProduct(Long productId, Integer quantity) {
        inventoryService.addInventories(productId, quantity);
    }
    
    // Remove multiple inventories from a product
    @Transactional
    public void removeInventoriesFromProduct(Long productId, Integer quantity) {
        inventoryService.removeInventories(productId, quantity);
    }
}
