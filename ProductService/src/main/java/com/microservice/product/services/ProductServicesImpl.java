package com.microservice.product.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseStructure<Product>> createProduct(Product product) {
    	if (productRepositories.findByProductName(product.getProductName()).isPresent()) {
    	    throw new RecordAlreadyExistException("Product already exists with name: " + product.getProductName());
    	}
    	if (product.getPrice() == null || product.getPrice() <= 0) {
    	    throw new RuleValidationException("Product price must be greater than 0");
    	}

    	if (product.getQuantity() == null || product.getQuantity() <= 0) {
    	    throw new RuleValidationException("Product quantity must be greater than 0");
    	}
        Product savedProduct = productRepositories.save(product);
        ResponseStructure<Product> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.CREATED.value());
        responseStructure.setMessage("Product created successfully");
        responseStructure.setData(savedProduct);
        return new ResponseEntity<>(responseStructure,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<Product>> getProductById(Integer productId) {

        Product product = productRepositories.findById(productId)
                .orElseThrow(() -> new ResourcesNotFoundException("Product not found with ID: " + productId));

        ResponseStructure<Product> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.OK.value());
        responseStructure.setMessage("Product found successfully");
        responseStructure.setData(product);
        return new ResponseEntity<>(responseStructure,HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<ResponseStructure<Product>> getProductByName(String productName) {

        Product product = productRepositories.findByProductName(productName)
                .orElseThrow(() -> new ResourcesNotFoundException("Product not found with Name: " + productName));

        ResponseStructure<Product> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.OK.value());
        responseStructure.setMessage("Product found successfully");
        responseStructure.setData(product);
        return new ResponseEntity<>(responseStructure,HttpStatus.OK);
    }

  
    @Override
    public ResponseEntity<ResponseStructure<Product>> updateProductById(Product product) {

        Product existingProduct = productRepositories.findById(product.getProductId())
                        .orElseThrow(() -> new ResourcesNotFoundException("Product not found with ID: "+ product.getProductId()));
        
        if (!existingProduct.getProductName().equals(product.getProductName()) && productRepositories.findByProductName(product.getProductName()).isPresent()) {
            throw new RecordAlreadyExistException("Product already exists with name: " + product.getProductName());
        }

        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RuleValidationException("Price must be greater than 0.");
        }
        if (product.getQuantity() == null || product.getQuantity() <= 0) {
            throw new RuleValidationException("Quantity must be greater than zero");
        }

        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());

        Product updatedProduct = productRepositories.save(existingProduct);
        ResponseStructure<Product> responseStructure = new ResponseStructure<>();

        responseStructure.setStatusCode(HttpStatus.OK.value());
        responseStructure.setMessage("Product updated successfully");
        responseStructure.setData(updatedProduct);
        return new ResponseEntity<>(responseStructure,HttpStatus.OK);
        }

    @Override
    public ResponseEntity<ResponseStructure<Product>> deleteProductById(Integer productId) {

        Product product = productRepositories.findById(productId)
                .orElseThrow(() ->
                        new ResourcesNotFoundException("Product not found with ID: " + productId));

        productRepositories.delete(product);

        ResponseStructure<Product> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.OK.value());
        responseStructure.setMessage("Product deleted successfully");
        responseStructure.setData(product);
        return new ResponseEntity<>(responseStructure,HttpStatus.OK);
    }
}