package com.microservice.product.controller;

import java.util.List;

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

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @PostMapping
    public ResponseEntity<ResponseStructure<Product>> createProduct(@RequestBody Product product) {

        ResponseStructure<Product> response = productServices.createProduct(product);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseStructure<Product>> getProduct(@PathVariable Integer productId) {

        ResponseStructure<Product> response = productServices.getProduct(productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts() {

        ResponseStructure<List<Product>> response = productServices.getAllProducts();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping
    public ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody Product product) {

        ResponseStructure<Product> response = productServices.updateProduct(product);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseStructure<String>> deleteProduct(@PathVariable Integer productId) {

        ResponseStructure<String> response = productServices.deleteProduct(productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}