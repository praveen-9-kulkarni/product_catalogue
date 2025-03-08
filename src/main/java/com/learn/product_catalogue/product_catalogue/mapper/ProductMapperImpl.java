package com.learn.product_catalogue.product_catalogue.mapper;

import org.springframework.stereotype.Component;

import com.learn.product_catalogue.dto.ProductCreateDTO;
import com.learn.product_catalogue.dto.ProductDTO;
import com.learn.product_catalogue.dto.ProductUpdateDTO;
import com.learn.product_catalogue.dto.mapper.ProductMapper;
import com.learn.product_catalogue.product_catalogue.model.Product;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDto(Object entity) {
        if (entity == null) {
            return null;
        }
        
        Product product = (Product) entity;
        
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .build();
    }

    @Override
    public Object toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        
        return product;
    }

    @Override
    public Object toEntity(ProductCreateDTO createDto) {
        if (createDto == null) {
            return null;
        }
        
        Product product = new Product();
        product.setName(createDto.getName());
        product.setDescription(createDto.getDescription());
        product.setPrice(createDto.getPrice());
        product.setCategory(createDto.getCategory());
        product.setImageUrl(createDto.getImageUrl());
        
        return product;
    }

    @Override
    public Object updateEntityFromDto(Object entity, ProductUpdateDTO updateDto) {
        if (entity == null || updateDto == null) {
            return entity;
        }
        
        Product product = (Product) entity;
        
        if (updateDto.getName() != null) {
            product.setName(updateDto.getName());
        }
        if (updateDto.getDescription() != null) {
            product.setDescription(updateDto.getDescription());
        }
        if (updateDto.getPrice() != null) {
            product.setPrice(updateDto.getPrice());
        }
        if (updateDto.getCategory() != null) {
            product.setCategory(updateDto.getCategory());
        }
        if (updateDto.getImageUrl() != null) {
            product.setImageUrl(updateDto.getImageUrl());
        }
        
        return product;
    }
} 