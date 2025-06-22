package com.second.festivalmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = {"username"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    // @Field("username")
    @NotNull(message = "User's username must not be null")
    private String username;
    @NotNull(message = "User's password must not be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull(message = "User's full name must not be null")
    private String fullName;

    @ElementCollection
    private Set<String> main_artist_in = new HashSet<>();
    @ElementCollection
    private Set<String> artist_in = new HashSet<>();
    @ElementCollection
    private Set<String> stage_manager = new HashSet<>();
    @ElementCollection
    private Set<String> organizer_in = new HashSet<>();
    @ElementCollection
    private Set<String> staff_in = new HashSet<>();

    // Constructors, Getters, Setters, etc.

    public User() {
    }

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }


}
