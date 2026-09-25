package controller;

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

import entities.User;
import services.UserService;
import util.ResponseStructure;

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
    public ResponseEntity<ResponseStructure<User>> getUser(@PathVariable String userId) {
        ResponseStructure<User> response = userService.getUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        ResponseStructure<List<User>> response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping
    public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user) {
        ResponseStructure<User> response = userService.updateUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseStructure<String>> deleteUser(@PathVariable String userId) {
        ResponseStructure<String> response = userService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
