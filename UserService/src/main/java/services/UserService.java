package services;

import java.util.List;

import entities.User;
import util.ResponseStructure;

public interface UserService {


    ResponseStructure<User> createUser(User user);

    ResponseStructure<User> getUser(String userId);

    ResponseStructure<List<User>> getAllUsers();

    ResponseStructure<User> updateUser(User user);

    ResponseStructure<String> deleteUser(String userId);
}
