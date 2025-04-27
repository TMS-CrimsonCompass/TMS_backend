package com.backend.CrimsonCompass.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true, nullable = true)
    private String authId;  // Added for OAuth integration

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)  // Changed to nullable for OAuth users
    private String password;

    private String phoneNumber;

    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum Role {
        traveler, admin, agent
    }

    // Optional: Constructor for OAuth users
    public User(String authId, String email, String firstName, String lastName) {
        this.authId = authId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = Role.traveler;  // Default role
    }

    public User() {
        // Default constructor required by JPA
    }
}
