package com.second.festivalmanagementsystem.controller;

import com.second.festivalmanagementsystem.exceptions.FestivalException;
import com.second.festivalmanagementsystem.model.Festival;
import com.second.festivalmanagementsystem.model.User;
import com.second.festivalmanagementsystem.service.FestivalService;
import com.second.festivalmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;
    @Autowired
    private UserService userService;

    // Create a new festival
    @PostMapping
    public ResponseEntity<?> createFestival(Authentication authentication, @RequestBody Festival festival) {
        User loggedUser = userService.getUserByUsername(authentication.getName());

        try {
            return ResponseEntity.ok(festivalService.createFestival(festival, loggedUser));
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Update an existing festival
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFestival(Authentication authentication, @PathVariable String id, @RequestBody Festival updatedFestival) {
        User loggedUser = userService.getUserByUsername(authentication.getName());

        try {
            return ResponseEntity.ok(festivalService.updateFestival(id, updatedFestival, loggedUser));
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/addorganizers")
    public ResponseEntity<?> addOrganizers(Authentication authentication, @PathVariable String id, @RequestBody ArrayList<String> organizers) {
        User loggedUser = userService.getUserByUsername(authentication.getName());

        try {
            return ResponseEntity.ok(festivalService.addOrganizers(id, loggedUser, organizers));
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/addstaff")
    public ResponseEntity<?> addStaff(Authentication authentication, @PathVariable String id, @RequestBody ArrayList<String> staff) {
        User loggedUser = userService.getUserByUsername(authentication.getName());

        try {
            return ResponseEntity.ok(festivalService.addStaff(id, loggedUser, staff));
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Get a festival by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFestivalById(@RequestHeader("Authorization") String authHeader, @PathVariable String id) {
        Optional<Festival> festival = festivalService.getFestivalById(id);
        if (festival.isPresent()) {
            return ResponseEntity.ok(festival.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFestivals(@RequestHeader("Authorization") String authHeader,
         @RequestParam(value = "name", required = false) String name,
         @RequestParam(value = "description", required = false) String description,
         @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
         @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
         @RequestParam(value = "venue", required = false) String venue) {

        List<Festival> festivals = festivalService.findFestivalsByFilters(name, description, startDate, endDate, venue);

        return ResponseEntity.ok(festivals);
    }


    // Delete a festival by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFestival(Authentication authentication,@PathVariable String id) {
        User loggedUser = userService.getUserByUsername(authentication.getName());
        try {
            festivalService.deleteFestival(id, loggedUser);
            return ResponseEntity.ok("Festival Deleted successfully");
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }
//
    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        try {
            festivalService.submitFestival(id);
        } catch (FestivalException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Festival submitted successfully");
    }
    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        try {
            festivalService.assignFestival(id);
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok("Festival assigned successfully");
   }
    @PostMapping("/{id}/review")
    public ResponseEntity<?> reviewFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {

        try {
            festivalService.reviewFestival(id);
        }catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        return ResponseEntity.ok("Festival got in review successfully");
    }
    @PostMapping("/{id}/schedule")
    public ResponseEntity<?> scheduleFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        try {
            festivalService.scheduleFestival(id);
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok("Festival got in schedule state successfully");
    }
    @PostMapping("/{id}/final")
    public ResponseEntity<?> finalFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        try {
            festivalService.finalFestival(id);
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok("Festival got in final state successfully");
    }
    @PostMapping("/{id}/decision")
    public ResponseEntity<?> decisionFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        try {
            festivalService.decisionFestival(id);
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok("Festival got in decision successfully");
    }
    @PostMapping("/{id}/announced")
    public ResponseEntity<?> announcedFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        try {
            festivalService.announcedFestival(id);
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok("Festival got in announced successfully");
    }

}
