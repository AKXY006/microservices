package com.microservice.order.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.order.entities.Order;
import com.microservice.order.repositories.OrderRepositories;
import com.microservice.order.util.ResponseStructure;

@Service
public class OrderServicesImpl implements OrderServices{
	
	    @Autowired
	    private OrderRepositories orderRepositories;

	@Override
	public ResponseStructure<Order> createOrder(Order order) {
		
		Order savedOrder = orderRepositories.save(order);

        ResponseStructure<Order> response = new ResponseStructure<>();
        response.setStatusCode(201);
        response.setMessage("Order created successfully");
        response.setData(savedOrder);
        return response;
	}

	@Override
	public ResponseStructure<Order> getOrder(Integer orderId) {
		Order order = orderRepositories.findById(orderId).orElse(null);

        ResponseStructure<Order> response = new ResponseStructure<>();

        if (order != null) {
            response.setStatusCode(200);
            response.setMessage("Order found successfully");
            response.setData(order);
        } else {
            response.setStatusCode(404);
            response.setMessage("Order not found");
            response.setData(null);
        }
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
	                orderRepositories.findById(order.getOrderId()).orElse(null);

	        ResponseStructure<Order> response = new ResponseStructure<>();

	        if (existingOrder != null) {
	            existingOrder.setUserId(order.getUserId());
	            existingOrder.setProductId(order.getProductId());
	            existingOrder.setQuantity(order.getQuantity());
	            existingOrder.setTotalPrice(order.getTotalPrice());
	            existingOrder.setStatus(order.getStatus());

	            Order updatedOrder = orderRepositories.save(existingOrder);
	            response.setStatusCode(200);
	            response.setMessage("Order updated successfully");
	            response.setData(updatedOrder);

	        } else {
	            response.setStatusCode(404);
	            response.setMessage("Order not found");
	            response.setData(null);
	        }
	        return response;
	}

	@Override
	public ResponseStructure<String> deleteOrder(Integer orderId) {
		
		ResponseStructure<String> response = new ResponseStructure<>();
        Order order = orderRepositories.findById(orderId).orElse(null);

        if (order != null) {
            orderRepositories.delete(order);
            response.setStatusCode(200);
            response.setMessage("Order deleted successfully");
            response.setData("Order with ID " + orderId + " deleted");

        } else {
            response.setStatusCode(404);
            response.setMessage("Order not found");
            response.setData(null);
        }
        return response;
    }
	}

  
