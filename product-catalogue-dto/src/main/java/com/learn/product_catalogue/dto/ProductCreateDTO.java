package com.learn.product_catalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for product creation requests.
 * This DTO doesn't include the id as it's not needed when creating a new product.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
} 