package com.learn.product_catalogue.product_catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.product_catalogue.product_catalogue.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
