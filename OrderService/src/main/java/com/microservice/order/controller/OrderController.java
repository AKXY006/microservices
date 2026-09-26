package com.microservice.order.controller;

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

import com.microservice.order.entities.Order;
import com.microservice.order.services.OrderServices;
import com.microservice.order.util.ResponseStructure;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @PostMapping
    public ResponseEntity<ResponseStructure<Order>> createOrder(@RequestBody Order order) {

        ResponseStructure<Order> response = orderServices.createOrder(order);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseStructure<Order>> getOrder(@PathVariable Integer orderId) {

        ResponseStructure<Order> response = orderServices.getOrder(orderId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Order>>> getAllOrders() {

        ResponseStructure<List<Order>> response = orderServices.getAllOrders();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseStructure<Order>> updateOrder(@PathVariable Integer orderId,@RequestBody Order order) {

        order.setOrderId(orderId);
       ResponseStructure<Order> response = orderServices.updateOrder(order);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ResponseStructure<String>> deleteOrder(@PathVariable Integer orderId) {

        ResponseStructure<String> response =orderServices.deleteOrder(orderId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}