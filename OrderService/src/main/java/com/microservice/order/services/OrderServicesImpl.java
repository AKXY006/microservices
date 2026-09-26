package com.microservice.order.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.order.entities.Order;
import com.microservice.order.exception.ResourcesNotFoundException;
import com.microservice.order.exception.RuleValidationException;
import com.microservice.order.repositories.OrderRepositories;
import com.microservice.order.util.ResponseStructure;

@Service
public class OrderServicesImpl implements OrderServices{
	
	    @Autowired
	    private OrderRepositories orderRepositories;

	 @Override
	 public ResponseStructure<Order> createOrder(Order order) {
		  if (order.getUserId() == null || order.getUserId() <= 0) {
		        throw new RuleValidationException("User ID must be greater than 0");
		    }

		    if (order.getProductId() == null || order.getProductId() <= 0) {
		        throw new RuleValidationException("Product ID must be greater than 0");
		    }

		    if (order.getQuantity() == null || order.getQuantity() <= 0) {
		        throw new RuleValidationException("Quantity must be greater than 0");
		    }

		    if (order.getTotalPrice() == null || order.getTotalPrice() <= 0) {
		        throw new RuleValidationException("Total price must be greater than 0");
		    }
		
		Order savedOrder = orderRepositories.save(order);
        ResponseStructure<Order> response = new ResponseStructure<>();
        response.setStatusCode(201);
        response.setMessage("Order created successfully");
        response.setData(savedOrder);
        return response;
	}

	@Override
	public ResponseStructure<Order> getOrder(Integer orderId) {
		
		 Order order = orderRepositories.findById(orderId)
		            .orElseThrow(() -> new ResourcesNotFoundException("Order not found with ID: " + orderId));
		 
            ResponseStructure<Order> response = new ResponseStructure<>();
            response.setStatusCode(200);
            response.setMessage("Order found successfully");
            response.setData(order);
       
            return response;
	   }

	@Override
	public ResponseStructure<List<Order>> getAllOrders() {
		 List<Order> orders = orderRepositories.findAll();

	        ResponseStructure<List<Order>> response = new ResponseStructure<>();
	        response.setStatusCode(200);
	        response.setMessage("All orders fetched successfully");
	        response.setData(orders);
	        return response;
	}
  
	@Override
	public ResponseStructure<Order> updateOrder(Order order) {
		 Order existingOrder =
	                orderRepositories.findById(order.getOrderId()).orElseThrow(()-> 
	                new ResourcesNotFoundException("Order not found with ID: " + order.getOrderId()));
		 
		   if (order.getUserId() == null || order.getUserId() <= 0) {
		        throw new RuleValidationException("User ID must be greater than 0");
		    }
		    if (order.getProductId() == null || order.getProductId() <= 0) {
		        throw new RuleValidationException("Product ID must be greater than 0");
		    }
		    if (order.getQuantity() == null || order.getQuantity() <= 0) {
		        throw new RuleValidationException("Quantity must be greater than 0");
		    }
		    if (order.getTotalPrice() == null || order.getTotalPrice() <= 0) {
		        throw new RuleValidationException("Total price must be greater than 0");
		    }
	            existingOrder.setUserId(order.getUserId());
	            existingOrder.setProductId(order.getProductId());
	            existingOrder.setQuantity(order.getQuantity());
	            existingOrder.setTotalPrice(order.getTotalPrice());
	            existingOrder.setStatus(order.getStatus());

	            Order updatedOrder = orderRepositories.save(existingOrder);
	            ResponseStructure<Order> response = new ResponseStructure<>();

	            response.setStatusCode(200);
	            response.setMessage("Order updated successfully");
	            response.setData(updatedOrder);

	      
	        return response;
	}

	@Override
	public ResponseStructure<String> deleteOrder(Integer orderId) {
		
		
        Order order = orderRepositories.findById(orderId).orElseThrow(() ->
        new ResourcesNotFoundException("Order not found with ID: " + orderId));
     
            orderRepositories.delete(order);
            ResponseStructure<String> response = new ResponseStructure<>();
            response.setStatusCode(200);
            response.setMessage("Order deleted successfully");
            response.setData("Order with ID " + orderId + " deleted");
        return response;
    }
	}

  
