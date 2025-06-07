package com.second.festivalmanagementsystem.model;

import com.second.festivalmanagementsystem.enums.PerformanceState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;

@Setter
@Getter
@Document(collection = "performances")
public class Performance {
    @Id
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
    private String festival_id;
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
