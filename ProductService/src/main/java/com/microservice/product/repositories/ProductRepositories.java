package com.microservice.product.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.product.entities.Product;

public interface ProductRepositories extends JpaRepository<Product, Integer> {

    Optional<Product> findByProductName(String productName);
}