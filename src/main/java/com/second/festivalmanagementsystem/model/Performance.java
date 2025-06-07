package com.second.festivalmanagementsystem.model;

import com.second.festivalmanagementsystem.enums.PerformanceState;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "performances")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private String genre;
    private int duration; // Duration in minutes
    private String festivalId; // Reference to Festival
    private List<String> bandMembers; // User IDs of band members
    private String technicalRequirements; // File path or content
    private PerformanceState state; // Possible values: CREATED, SUBMITTED, REVIEWED, etc.

    private User main_artist;
    private HashSet<User> artists;
    private User stage_manager;
    // Constructors, Getters, Setters, etc.

    public Performance() {}

    public Performance(String name, String description, String genre, int duration, String festivalId, List<String> bandMembers, String technicalRequirements, PerformanceState state) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.duration = duration;
        this.festivalId = festivalId;
        this.bandMembers = bandMembers;
        this.technicalRequirements = technicalRequirements;
        this.state = state;
    }

}
