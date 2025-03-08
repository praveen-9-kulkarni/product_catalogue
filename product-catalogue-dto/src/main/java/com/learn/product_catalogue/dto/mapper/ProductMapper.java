package com.learn.product_catalogue.dto.mapper;

import com.learn.product_catalogue.dto.ProductCreateDTO;
import com.learn.product_catalogue.dto.ProductDTO;
import com.learn.product_catalogue.dto.ProductUpdateDTO;

/**
 * Interface for mapping between Product entity and DTOs.
 * This interface is meant to be implemented in the main application module
 * where the actual entity class is available.
 */
public interface ProductMapper {
    
    /**
     * Converts a Product entity to a ProductDTO.
     * 
     * @param entity the Product entity
     * @return the ProductDTO
     */
    ProductDTO toDto(Object entity);

    /**
     * Converts a ProductDTO to a Product entity.
     * 
     * @param dto the ProductDTO
     * @return the Product entity
     */
    Object toEntity(ProductDTO dto);
    
    /**
     * Converts a ProductCreateDTO to a Product entity.
     * 
     * @param createDto the ProductCreateDTO
     * @return the Product entity
     */
    Object toEntity(ProductCreateDTO createDto);
    
    /**
     * Updates a Product entity with data from a ProductUpdateDTO.
     * 
     * @param entity the Product entity to update
     * @param updateDto the ProductUpdateDTO with updated data
     * @return the updated Product entity
     */
    Object updateEntityFromDto(Object entity, ProductUpdateDTO updateDto);
} 