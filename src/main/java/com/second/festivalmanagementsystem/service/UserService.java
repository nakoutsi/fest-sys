package com.second.festivalmanagementsystem.service;

import com.second.festivalmanagementsystem.exceptions.UserException;
import com.second.festivalmanagementsystem.model.User;
import com.second.festivalmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;



    public User registerUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }
        return userRepository.save(user);
    }
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }



    public User authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password.");
        }
        return user.get();
    }



    public User validateUser(String authHeader) throws UserException {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            logger.debug("Authorization header received: {}", authHeader);
            String base64Credentials = authHeader.substring("Basic ".length());
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);
            logger.debug("Extracted username: {}", values[0]);
            String username = values[0];
            String password = values[1];

            Optional<User> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                logger.warn("No user found with username: {}", username);
                throw new UserException("Username does not exists");
            }
            if (!user.get().getPassword().equals(password)) {
                    throw new UserException("Invalid username or password.");
            }
            return user.get();

        }

        throw new UserException("No authheader provided");
    }

}
