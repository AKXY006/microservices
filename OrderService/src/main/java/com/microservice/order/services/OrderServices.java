package com.microservice.order.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.microservice.order.entities.Order;
import com.microservice.order.util.ResponseStructure;

public interface OrderServices {
	
	ResponseEntity<ResponseStructure<Order>> createOrder(Order order);

	ResponseEntity<ResponseStructure<Order>> getOrderById(Integer orderId);

	ResponseEntity<ResponseStructure<List<Order>>> getAllOrders();

    ResponseEntity<ResponseStructure<Order>> updateOrderStatusById(Integer orderId, Order order);

    ResponseEntity<ResponseStructure<Order>> deleteOrderById(Integer orderId);
    
    ResponseEntity<ResponseStructure<List<Order>>> getOrdersByUserId(Integer userId);
    

}
