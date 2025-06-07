package com.second.festivalmanagementsystem.controller;

import com.second.festivalmanagementsystem.model.User;
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
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException ex) {
            // Handle duplicate username case
            return ResponseEntity.badRequest().body("Username already exists.");
        }
    }

    // Authenticate a user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        try {
            User authenticatedUser = userService.authenticateUser(username, password);
            return ResponseEntity.ok(authenticatedUser);
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
