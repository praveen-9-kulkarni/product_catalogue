package com.learn.product_catalogue.product_catalogue.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.product_catalogue.dto.ProductCreateDTO;
import com.learn.product_catalogue.dto.ProductDTO;
import com.learn.product_catalogue.dto.ProductUpdateDTO;
import com.learn.product_catalogue.product_catalogue.api.response.ErrorResponse;
import com.learn.product_catalogue.product_catalogue.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductCreateDTO createDto) {
        ProductDTO savedProduct = productService.saveProduct(createDto);
        return ResponseEntity.created(URI.create("/api/products/" + savedProduct.getId()))
                             .body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductUpdateDTO updateDto) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, updateDto);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id)
                             .map(product -> {
                                productService.deleteProduct(id);
                                return ResponseEntity.ok().<Void>build();
                             })
                             .orElse(ResponseEntity.notFound().build());
    }
    
    // Get total inventory count for a product
    @GetMapping("/{id}/inventory/count")
    public ResponseEntity<?> getInventoryCount(@PathVariable("id") Long id) {
        try {
            Integer count = productService.getProductInventoryCount(id);
            Map<String, Integer> response = new HashMap<>();
            response.put("quantity", count);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    
    // Add inventories to product
    @PostMapping("/{id}/inventory/add")
    public ResponseEntity<?> addInventories(
            @PathVariable("id") Long id,
            @RequestParam("quantity") Integer quantity) {
        
        try {
            productService.addInventoriesToProduct(id, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    
    // Remove inventories from product
    @PostMapping("/{id}/inventory/remove")
    public ResponseEntity<?> removeInventories(
            @PathVariable("id") Long id,
            @RequestParam("quantity") Integer quantity) {
        
        try {
            productService.removeInventoriesFromProduct(id, quantity);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (IllegalStateException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
