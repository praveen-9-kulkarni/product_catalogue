package com.learn.product_catalogue.product_catalogue.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learn.product_catalogue.product_catalogue.model.Product;
import com.learn.product_catalogue.product_catalogue.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
