package com.micro.userservice.services;

import java.util.List;

import com.micro.userservice.entities.User;
import com.micro.userservice.util.ResponseStructure;


public interface UserService {


    ResponseStructure<User> createUser(User user);

    ResponseStructure<User> getUser(Integer userId);

    ResponseStructure<List<User>> getAllUsers();

    ResponseStructure<User> updateUser(User user);

    ResponseStructure<String> deleteUser(Integer userId);
}
