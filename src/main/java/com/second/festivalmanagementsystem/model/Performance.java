package com.second.festivalmanagementsystem.model;

import com.second.festivalmanagementsystem.enums.PerformanceState;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @ManyToOne
    @JoinColumn(name = "festival_id")
    private Festival festival; // Reference to Festival
    @ElementCollection
    private List<String> bandMembers; // User IDs of band members
    private String technicalRequirements; // File path or content
    private PerformanceState state; // Possible values: CREATED, SUBMITTED, REVIEWED, etc.

    @ManyToOne
    @JoinColumn(name = "main_artist_id")
    private User main_artist;

    @ManyToMany
    @JoinTable(name = "performance_artists",
            joinColumns = @JoinColumn(name = "performance_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> artists = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "stage_manager_id")
    private User stage_manager;
    // Constructors, Getters, Setters, etc.

    public Performance() {}

    public Performance(String name, String description, String genre, int duration, Festival festival, List<String> bandMembers, String technicalRequirements, PerformanceState state) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.duration = duration;
        this.festival = festival;
        this.bandMembers = bandMembers;
        this.technicalRequirements = technicalRequirements;
        this.state = state;
    }

}
