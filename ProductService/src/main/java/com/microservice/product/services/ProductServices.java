package com.microservice.product.services;


import org.springframework.http.ResponseEntity;

import com.microservice.product.entities.Product;
import com.microservice.product.util.ResponseStructure;

public interface ProductServices {

    ResponseEntity<ResponseStructure<Product>> createProduct(Product product);

    ResponseEntity<ResponseStructure<Product>> getProductById(Integer productId);
    
    ResponseEntity<ResponseStructure<Product>> getProductByName(String productName);

    ResponseEntity<ResponseStructure<Product>> updateProductById(Product product);

    ResponseEntity<ResponseStructure<Product>> deleteProductById(Integer productId);
    
}