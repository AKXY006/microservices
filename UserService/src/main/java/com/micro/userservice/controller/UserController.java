package com.micro.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.userservice.client.OrderClient;
import com.micro.userservice.entities.User;
import com.micro.userservice.services.UserService;
import com.micro.userservice.util.ResponseStructure;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private OrderClient orderClient;

    @PostMapping
    public ResponseEntity<ResponseStructure<User>> createUser(@RequestBody User user) {
         return userService.createUser(user);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseStructure<User>> getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseStructure<List<User>>> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<ResponseStructure<User>> updateUser(@PathVariable Integer userId , @RequestBody User user) {
        user.setUserId(userId);
        return userService.updateUser(user);
    }

//    @GetMapping
//    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
//        return userService.getAllUsers();
//    }
    
    @GetMapping
    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers(@RequestHeader("X-Gateway") String gateway) {
        System.out.println("Request received in UserController");
        System.out.println("X-Gateway Header: " + gateway);
        return userService.getAllUsers();
    }
    
    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Integer userId) {
        return userService.getOrdersByUserId(userId);
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseStructure<User>> deleteUserById(@PathVariable Integer userId) {
        return userService.deleteUserById(userId);
    }
    
    @GetMapping("/test-loadbalancer")
    public String testLoadBalancer() {
        return orderClient.getOrderServiceInstance();
    }
}
