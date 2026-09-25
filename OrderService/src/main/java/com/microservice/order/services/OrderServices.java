package com.microservice.order.services;

import java.util.List;

import com.microservice.order.entities.Order;
import com.microservice.order.util.ResponseStructure;

public interface OrderServices {
	
	ResponseStructure<Order> createOrder(Order order);

    ResponseStructure<Order> getOrder(Integer orderId);

    ResponseStructure<List<Order>> getAllOrders();

    ResponseStructure<Order> updateOrder(Order order);

    ResponseStructure<String> deleteOrder(Integer orderId);

}
