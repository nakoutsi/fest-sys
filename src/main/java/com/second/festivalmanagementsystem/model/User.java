package com.second.festivalmanagementsystem.model;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Objects;

@Setter
@Getter
@Document(collection = "users")
@EqualsAndHashCode(of = {"username"})
public class User {
    @Id
    private String id;
    // @Field("username")
    @NotNull(message = "User's username must not be null")
    private String username;
    @NotNull(message = "User's password must not be null")
    private String password;
    @NotNull(message = "User's full name must not be null")
    private String fullName;

    private HashSet<String> main_artist_in = new HashSet<>();
    private HashSet<String> artist_in = new HashSet<>();
    private HashSet<String> stage_manager = new HashSet<>();
    private HashSet<String> organizer_in = new HashSet<>();
    private HashSet<String> staff_in = new HashSet<>();

    // Constructors, Getters, Setters, etc.

    public User() {
    }

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }


}
