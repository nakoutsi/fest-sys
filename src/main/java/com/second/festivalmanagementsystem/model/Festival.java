package com.second.festivalmanagementsystem.model;

import com.second.festivalmanagementsystem.enums.FestivalState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "festivals")
public class Festival {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDate created = LocalDate.now();
    @Column(unique = true)
    @NotNull(message = "Festival's name must not be null")
    private String name;

    @NotNull(message = "Festival's description must not be null")
    private String description;

    @NotNull(message = "Festival's description must not be null")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "Festival's description must not be null")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @NotNull(message = "Festival's description must not be null")
    private String venue;

    private String budget;
    private String vendorManagement;
    private FestivalState state = FestivalState.CREATED; // Possible values: CREATED, SUBMISSION, ASSIGNMENT, etc.

    @ManyToMany
    @JoinTable(name = "festival_organizers",
            joinColumns = @JoinColumn(name = "festival_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> organizers = new HashSet<>();

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Performance> performances = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "festival_staff",
            joinColumns = @JoinColumn(name = "festival_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> staff = new HashSet<>();


    // Constructors, Getters, Setters, etc.

    public Festival() {
    }

    public Festival(String name, String description, Date startDate, Date endDate, String venue, FestivalState state) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.state = state;
    }

}
