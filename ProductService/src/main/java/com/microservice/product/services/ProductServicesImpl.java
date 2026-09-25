package com.microservice.product.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.product.entities.Product;
import com.microservice.product.exception.RecordAlreadyExistException;
import com.microservice.product.exception.ResourcesNotFoundException;
import com.microservice.product.exception.RuleValidationException;
import com.microservice.product.repositories.ProductRepositories;
import com.microservice.product.util.ResponseStructure;

@Service
public class ProductServicesImpl implements ProductServices {

    @Autowired
    private ProductRepositories productRepositories;

    @Override
    public ResponseStructure<Product> createProduct(Product product) {

        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new RuleValidationException("Product name cannot be empty.");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RuleValidationException("Price must be greater than 0.");
        }
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new RuleValidationException("Quantity cannot be negative.");
        }

        Optional<Product> existingProduct = productRepositories.findByProductName( product.getProductName());

        if (existingProduct.isPresent()) {
            throw new RecordAlreadyExistException("Product with this name already exists.");
        }

        Product savedProduct = productRepositories.save(product);

        ResponseStructure<Product> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Product created successfully");
        response.setData(savedProduct);
        return response;
    }

    @Override
    public ResponseStructure<Product> getProduct(Integer productId) {

        Product product = productRepositories.findById(productId)
                .orElseThrow(() ->
                        new ResourcesNotFoundException("Product not found with ID: " + productId));

        ResponseStructure<Product> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Product found successfully");
        response.setData(product);
        return response;
    }

    @Override
    public ResponseStructure<List<Product>> getAllProducts() {

        List<Product> products = productRepositories.findAll();

        ResponseStructure<List<Product>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Products fetched successfully");
        response.setData(products);
        return response;
    }

    @Override
    public ResponseStructure<Product> updateProduct(Product product) {

        Product existingProduct = productRepositories.findById(product.getProductId())
                        .orElseThrow(() ->
                                new ResourcesNotFoundException("Product not found with ID: "+ product.getProductId()));

        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new RuleValidationException("Product name cannot be empty.");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RuleValidationException("Price must be greater than 0.");
        }
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new RuleValidationException("Quantity cannot be negative.");
        }

        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());

        Product updatedProduct = productRepositories.save(existingProduct);
        ResponseStructure<Product> response = new ResponseStructure<>();

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Product updated successfully");
        response.setData(updatedProduct);
        return response;
    }

    @Override
    public ResponseStructure<String> deleteProduct(Integer productId) {

        Product product = productRepositories.findById(productId)
                .orElseThrow(() ->
                        new ResourcesNotFoundException("Product not found with ID: " + productId));

        productRepositories.delete(product);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Product deleted successfully");
        response.setData("Product with ID " + productId +" deleted successfully");
        return response;
    }
}