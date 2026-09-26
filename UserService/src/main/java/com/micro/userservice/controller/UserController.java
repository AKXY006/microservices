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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.userservice.entities.User;
import com.micro.userservice.services.UserService;
import com.micro.userservice.util.ResponseStructure;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseStructure<User>> createUser(@RequestBody User user) {
        ResponseStructure<User> response = userService.createUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseStructure<User>> getUser(@PathVariable Integer userId) {
        ResponseStructure<User> response = userService.getUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        ResponseStructure<List<User>> response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseStructure<User>> updateUser(@PathVariable Integer userId,@RequestBody User user) {

        user.setUserId(userId);
        ResponseStructure<User> response =userService.updateUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseStructure<String>> deleteUser(@PathVariable Integer userId) {
        ResponseStructure<String> response = userService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
