package com.microservice.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.product.entities.Product;
import com.microservice.product.services.ProductServices;
import com.microservice.product.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @PostMapping
    public ResponseEntity<ResponseStructure<Product>> createProduct(@Valid @RequestBody Product product) {
        return productServices.createProduct(product);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseStructure<Product>> getProductById(@PathVariable Integer productId) {
        return productServices.getProductById(productId);
    }
    
    @GetMapping("/name/{productName}")
    public ResponseEntity<ResponseStructure<Product>> getProductByName(@PathVariable String productName) {
        return productServices.getProductByName(productName);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ResponseStructure<Product>> updateProduct(@PathVariable Integer productId,@Valid @RequestBody Product product) {
        product.setProductId(productId);
        return productServices.updateProductById(product);
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseStructure<Product>> deleteProductById(@PathVariable Integer productId) {
        return productServices.deleteProductById(productId);
    }
}