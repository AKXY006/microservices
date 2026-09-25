package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import entities.User;
import exception.RecordAlreadyExistException;
import exception.ResourceNotFoundException;
import exception.RuleValidationException;
import repositories.UserRepository;
import util.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseStructure<User> createUser(User user) {

        Optional<User> emailUser = userRepository.findByEmail(user.getEmail());

        if (emailUser.isPresent()) {
            throw new RecordAlreadyExistException("User with Email already exists.");
        }

        if (user.getUserId() == null || !user.getUserId().matches("\\d+")) {
            throw new RuleValidationException("User ID must contain only numbers.");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new RuleValidationException("Name cannot be empty.");
        }

        if (user.getName().length() > 25) {
            throw new RuleValidationException("Name must be 25 characters or less.");
        }

        if (!user.getName().matches("[a-zA-Z ]+")) {
            throw new RuleValidationException("Name must contain only alphabets.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuleValidationException("Email cannot be empty.");
        }

        User savedUser = userRepository.save(user);

        ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User created successfully");
        response.setData(savedUser);

        return response;
    }

    @Override
    public ResponseStructure<User> getUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + userId));

        ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User found successfully");
        response.setData(user);

        return response;
    }

    @Override
    public ResponseStructure<List<User>> getAllUsers() {

        List<User> users = userRepository.findAll();

        ResponseStructure<List<User>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users fetched successfully");
        response.setData(users);

        return response;
    }

    @Override
    public ResponseStructure<User> updateUser(User user) {

        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID: " + user.getUserId()));

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new RuleValidationException("Name cannot be empty.");
        }

        if (user.getName().length() > 25) {
            throw new RuleValidationException("Name must be 25 characters or less.");
        }

        if (!user.getName().matches("[a-zA-Z ]+")) {
            throw new RuleValidationException("Name must contain only alphabets.");
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());

        User updatedUser = userRepository.save(existingUser);

        ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User updated successfully");
        response.setData(updatedUser);

        return response;
    }

    @Override
    public ResponseStructure<String> deleteUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID: " + userId));

        userRepository.delete(user);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        response.setData("User with ID " + userId + " deleted successfully");

        return response;
    }
}