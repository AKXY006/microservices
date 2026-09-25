package com.microservice.product.services;

import java.util.List;

import com.microservice.product.entities.Product;
import com.microservice.product.util.ResponseStructure;

public interface ProductServices {

    ResponseStructure<Product> createProduct(Product product);

    ResponseStructure<Product> getProduct(Integer productId);

    ResponseStructure<List<Product>> getAllProducts();

    ResponseStructure<Product> updateProduct(Product product);

    ResponseStructure<String> deleteProduct(Integer productId);
}