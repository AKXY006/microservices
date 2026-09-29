package com.microservice.order.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservice.order.client.ProductClient;
import com.microservice.order.dto.ProductDto;
import com.microservice.order.entities.Order;
import com.microservice.order.entities.Status;
import com.microservice.order.exception.ResourcesNotFoundException;
import com.microservice.order.exception.RuleValidationException;
import com.microservice.order.repositories.OrderRepositories;
import com.microservice.order.util.ResponseStructure;



@Service
public class OrderServicesImpl implements OrderServices{
	
	    @Autowired
	    private OrderRepositories orderRepositories;
	    
	    @Autowired
	    private ProductClient productClient;
	    
	    @Autowired
	    private CircuitBreakerFactory circuitBreakerFactory;

	        @Override
	        public ResponseEntity<ResponseStructure<Order>> createOrder(Order order) {
		 
		    if(order.getUserId() == null || order.getUserId() <= 0) {
		        throw new RuleValidationException("User ID must be greater than 0");
		    }
		    if(order.getProductId() == null || order.getProductId() <= 0) {
		        throw new RuleValidationException("Product ID must be greater than 0");
		    }
		    if(order.getQuantity() == null || order.getQuantity() <= 0) {
		        throw new RuleValidationException("Quantity must be greater than 0");
		    }
		    
//		    ResponseStructure<ProductDto> productResponse = productClient.getProductById(order.getProductId());
		    
		    //REPLACE
		    CircuitBreaker circuitBreaker = circuitBreakerFactory.create("productService");
//
//		    ResponseStructure<ProductDto> productResponse = circuitBreaker.run(
//		                () -> productClient.getProductById(order.getProductId()),
//		                throwable -> {
//		                    throw new RuntimeException("Product Service is currently unavailable");
//		                }
//		            );
		    
		    ResponseStructure<ProductDto> productResponse = circuitBreaker.run(
		                 () -> productClient.getProductById(order.getProductId()),
		            throwable -> {
		                System.out.println("Circuit Breaker Error: " + throwable.getMessage());
		                throwable.printStackTrace();
		                throw new RuntimeException("Product Service is currently unavailable: " + throwable.getMessage());
		            }
		    );

		    ProductDto product = productResponse.getData();

		    if (product.getQuantity() < order.getQuantity()) {
		        throw new RuleValidationException("Insufficient product quantity");
		    }
		    
		    double totalPrice = product.getPrice() * order.getQuantity();
		    order.setTotalPrice(totalPrice);
		    order.setStatus(Status.PLACED);
		
		    Order savedOrder = orderRepositories.save(order);
		
           ResponseStructure<Order> responseStructure = new ResponseStructure<>();
           responseStructure.setStatusCode(HttpStatus.CREATED.value());
           responseStructure.setMessage("Order created successfully");
           responseStructure.setData(savedOrder);
           return new ResponseEntity<>(responseStructure,HttpStatus.CREATED);
	       }

	       @Override
	       public ResponseEntity<ResponseStructure<Order>> getOrderById(Integer orderId) {
		
		    Order order = orderRepositories.findById(orderId)
		            .orElseThrow(() -> new ResourcesNotFoundException("Order not found with ID: " + orderId));
		 
            ResponseStructure<Order> responseStructure = new ResponseStructure<>();
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setMessage("Order found successfully");
            responseStructure.setData(order);
            return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	       }

	        @Override
	        public ResponseEntity<ResponseStructure<List<Order>>> getAllOrders() {
		    List<Order> orders = orderRepositories.findAll();
	        ResponseStructure<List<Order>> responseStructure = new ResponseStructure<>();
	        responseStructure.setStatusCode(HttpStatus.OK.value());
	        responseStructure.setMessage("All orders fetched successfully");
	        responseStructure.setData(orders);
	        return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	        }
  
	        @Override
	        public ResponseEntity<ResponseStructure<Order>> updateOrderStatusById(Integer orderId, Order order) {
		 
		    Order existingOrder = orderRepositories.findById(orderId)
		            .orElseThrow(() ->
		                    new ResourcesNotFoundException(
		                            "Order not found with ID: " + orderId));
		    
	         if (order.getStatus() == null) {
		        throw new RuleValidationException("Order status is required");
		     }
	         existingOrder.setStatus(order.getStatus());

	         Order updatedOrder = orderRepositories.save(existingOrder);
	         ResponseStructure<Order> responseStructure = new ResponseStructure<>();
	         responseStructure.setStatusCode(HttpStatus.OK.value());
	         responseStructure.setMessage("Order updated successfully");
	         responseStructure.setData(updatedOrder);
	         return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	        }

	        @Override
	        public ResponseEntity<ResponseStructure<Order>> deleteOrderById(Integer orderId) {
		
            Order order = orderRepositories.findById(orderId).orElseThrow(() ->
            new ResourcesNotFoundException("Order not found with ID: " + orderId));
     
            orderRepositories.delete(order);
            ResponseStructure<Order> responseStructure = new ResponseStructure<>();
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setMessage("Order deleted successfully");
            responseStructure.setData(order);
            return new ResponseEntity<>(responseStructure,HttpStatus.OK);
            }
	        
	        @Override
	        public ResponseEntity<ResponseStructure<List<Order>>> getOrdersByUserId(Integer userId) {

	            List<Order> orders = orderRepositories.findByUserId(userId);

	            ResponseStructure<List<Order>> responseStructure = new ResponseStructure<>();
	            responseStructure.setStatusCode(HttpStatus.OK.value());
	            responseStructure.setMessage("Orders fetched successfully");
	            responseStructure.setData(orders);

	            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	        }
	        
	        @Override
	        public boolean hasActiveOrder(Integer userId) {
            List<Status> activeStatuses = List.of(Status.PLACED,Status.CONFIRMED,Status.SHIPPED);
            return orderRepositories.existsByUserIdAndStatusIn(userId,activeStatuses);
	        }
	        
	        
	        @Override
	        public boolean hasActiveOrderByProductId(Integer productId) {
	            List<Status> activeStatuses = List.of(Status.PLACED,Status.CONFIRMED,Status.SHIPPED);
	            return orderRepositories.existsByProductIdAndStatusIn(productId,activeStatuses);
	        }
	        
	        }

  
