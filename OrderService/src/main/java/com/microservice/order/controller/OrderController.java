package com.microservice.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.order.entities.Order;
import com.microservice.order.services.OrderServices;
import com.microservice.order.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @PostMapping
    public ResponseEntity<ResponseStructure<Order>> createOrder(@Valid @RequestBody Order order) {
        return orderServices.createOrder(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseStructure<Order>> getOrderById(@PathVariable Integer orderId) {
        return orderServices.getOrderById(orderId);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Order>>> getAllOrders() {
    return orderServices.getAllOrders();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseStructure<Order>> updateOrderStatusById(@PathVariable Integer orderId, @Valid @RequestBody Order order) {
        order.setOrderId(orderId);
        return orderServices.updateOrderStatusById(orderId, order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ResponseStructure<Order>> deleteOrderById(@PathVariable Integer orderId) {
        return orderServices.deleteOrderById(orderId);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<List<Order>>> getOrdersByUserId(@PathVariable Integer userId) {
        return orderServices.getOrdersByUserId(userId);
    }
    
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<Boolean> hasActiveOrder(@PathVariable Integer userId) {
        boolean active = orderServices.hasActiveOrder(userId);
        return new ResponseEntity<>(active, HttpStatus.OK);
    }
    
    @GetMapping("/product/{productId}/active")
    public ResponseEntity<Boolean> hasActiveOrderByProductId(@PathVariable Integer productId) {
        boolean active = orderServices.hasActiveOrderByProductId(productId);
        return new ResponseEntity<>(active, HttpStatus.OK);
    }
}