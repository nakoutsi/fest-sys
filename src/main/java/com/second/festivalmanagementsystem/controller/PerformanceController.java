package com.second.festivalmanagementsystem.controller;

import com.second.festivalmanagementsystem.enums.PerformanceState;
import com.second.festivalmanagementsystem.exceptions.FestivalException;
import com.second.festivalmanagementsystem.exceptions.PerformanceException;
import com.second.festivalmanagementsystem.exceptions.UserException;
import com.second.festivalmanagementsystem.model.Performance;
import com.second.festivalmanagementsystem.model.User;
import com.second.festivalmanagementsystem.service.FestivalService;
import com.second.festivalmanagementsystem.service.PerformanceService;
import com.second.festivalmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private FestivalService festivalService;
    @Autowired
    private UserService userService;

    // Create a new performance
    @PostMapping("/{id}")
    public ResponseEntity<?> createPerformance(@RequestBody Performance performance,@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        try {
            // Validate required fields
            if (performance.getName() == null || performance.getDescription() == null || performance.getGenre() == null) {
                return ResponseEntity.badRequest().body("Missing required fields: name, description, or genre.");
            }

            User loggedUser = null;
            try {
                loggedUser = userService.validateUser(authHeader);
            } catch (UserException e) {
                return ResponseEntity.status(400).body("wrong username or password");
            }

            performance.setState(PerformanceState.CREATED); // Default state
            Performance createdPerformance = performanceService.createPerformance(performance, loggedUser, id);
            return ResponseEntity.ok(createdPerformance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating performance: " + e.getMessage());
        }
    }
    @PutMapping("/{id}/submit")
    public ResponseEntity<?> submitPerformance(@PathVariable String id,  @RequestHeader("Authorization") String authHeader) {
        try {
            User loggedUser = null;
            try {
                loggedUser = userService.validateUser(authHeader);
            } catch (UserException e) {
                return ResponseEntity.status(400).body("wrong username or password");
            }

            // Change state to SUBMITTED
            Performance submittedPerformance = performanceService.submitPerformance(id, loggedUser);
            return ResponseEntity.ok(submittedPerformance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting performance: " + e.getMessage());
        }
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchPerformances(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String festivalId) {
        try {
            List<Performance> results = performanceService.searchPerformances(genre, festivalId);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error searching performances: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/addartist")
    public ResponseEntity<?> addArtists(@RequestHeader("Authorization") String authHeader, @PathVariable String id, @RequestBody String artist ) {
        User loggedUser = null;
        try {
            loggedUser = userService.validateUser(authHeader);
        } catch (UserException e) {
            return ResponseEntity.status(400).body("wrong username or password");
        }


        try {
            return ResponseEntity.ok(performanceService.addArtist(id, loggedUser, artist));
        } catch (PerformanceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    // Update an existing performance
    @PutMapping("/{id}")
    public ResponseEntity<Performance> updatePerformance(@PathVariable String id, @RequestBody Performance updatedPerformance) {
        return ResponseEntity.ok(performanceService.updatePerformance(id, updatedPerformance));
    }

    // Get all performances by festival ID
    @GetMapping("/festival/{festivalId}")
    public ResponseEntity<List<Performance>> getPerformancesByFestival(@PathVariable String festivalId) {
        return ResponseEntity.ok(performanceService.getPerformancesByFestival(festivalId));
    }

    // Delete a performance by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable String id) {
        performanceService.deletePerformance(id);
        return ResponseEntity.noContent().build();
    }




}
