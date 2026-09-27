package com.micro.userservice.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.micro.userservice.entities.User;
import com.micro.userservice.util.ResponseStructure;


public interface UserService {


    ResponseEntity<ResponseStructure<User>> createUser(User user);

    ResponseEntity<ResponseStructure<User>> getUserById(Integer userId);
    
    ResponseEntity<ResponseStructure<List<User>>> getUserByName(String name);

    ResponseEntity<ResponseStructure<List<User>>> getAllUsers();

    ResponseEntity<ResponseStructure<User>> updateUser(User user);
    
    ResponseEntity<?> getOrdersByUserId(Integer userId);
    
}
