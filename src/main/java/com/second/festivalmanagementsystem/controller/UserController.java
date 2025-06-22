package com.second.festivalmanagementsystem.controller;

import com.second.festivalmanagementsystem.model.User;
import com.second.festivalmanagementsystem.dto.UserResponseDto;
import com.second.festivalmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            UserResponseDto dto = UserResponseDto.fromUser(registeredUser);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            // Handle duplicate username case
            return ResponseEntity.badRequest().body("Username already exists.");
        }
    }

    // Authenticate a user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        try {
            User authenticatedUser = userService.authenticateUser(
                    loginRequest.getUsername(),
                    loginRequest.getPassword());
            UserResponseDto dto = UserResponseDto.fromUser(authenticatedUser);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            // Handle invalid credentials case
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }
    }
    // Fetch a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException ex) {
            // Handle user not found case
            return ResponseEntity.badRequest().body("User not found.");
        }
    }


}
