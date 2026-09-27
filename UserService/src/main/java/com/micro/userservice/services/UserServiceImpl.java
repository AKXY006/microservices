package com.micro.userservice.services;

import java.util.List;

import com.micro.userservice.client.OrderClient;
import com.micro.userservice.dto.OrderDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.micro.userservice.entities.User;
import com.micro.userservice.exception.RecordAlreadyExistException;
import com.micro.userservice.exception.ResourceNotFoundException;
import com.micro.userservice.repositories.UserRepository;
import com.micro.userservice.util.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderClient orderClient;

    @Override
    public ResponseEntity<ResponseStructure<User>> createUser(User user) {
    	if (userRepository.findByEmail(user.getEmail()).isPresent()) {
    	    throw new RecordAlreadyExistException("User already exists with email: " + user.getEmail());
    	}
        User savedUser = userRepository.save(user);
        ResponseStructure<User> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.CREATED.value());
        responseStructure.setMessage("User created successfully");
        responseStructure.setData(savedUser);
        return new ResponseEntity<>(responseStructure,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<User>> getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        ResponseStructure<User> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.OK.value());
        responseStructure.setMessage("User found successfully");
        responseStructure.setData(user);
        return new ResponseEntity<>(responseStructure,HttpStatus.OK);
       
    }
    

	@Override
	public ResponseEntity<ResponseStructure<List<User>>> getUserByName(String name) {
		List<User> users = userRepository.findByName(name); 
		if (users.isEmpty()) {
	        throw new ResourceNotFoundException("User not found with Name: " + name);
	    }
	        ResponseStructure<List<User>> responseStructure = new ResponseStructure<>();
	        responseStructure.setStatusCode(HttpStatus.OK.value());
	        responseStructure.setMessage("User found successfully");
	        responseStructure.setData(users);
	        return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	}
    
  
    @Override
    public ResponseEntity<ResponseStructure<User>> updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + user.getUserId()));
        
        if (!existingUser.getEmail().equals(user.getEmail()) && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RecordAlreadyExistException( "User already exists with email: " + user.getEmail());
        }
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());

        User updatedUser = userRepository.save(existingUser);
        ResponseStructure<User> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.OK.value());
        responseStructure.setMessage("User updated successfully");
        responseStructure.setData(updatedUser);
        return new ResponseEntity<>(responseStructure,HttpStatus.OK);   
    }
    
    @Override
    public  ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        List<User> users = userRepository.findAll();
        ResponseStructure<List<User>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.OK.value());
        responseStructure.setMessage("Users fetched successfully");
        responseStructure.setData(users);
        return new ResponseEntity<>(responseStructure,HttpStatus.OK); 
    }
    
    @Override
    public ResponseEntity<?> getOrdersByUserId(Integer userId) {
        ResponseStructure<List<OrderDto>> orderResponse = orderClient.getOrderByUserId(userId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }


}