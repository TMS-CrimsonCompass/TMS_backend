package com.backend.CrimsonCompass.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Table(name = "Places")
@Getter
@Setter
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int placeId;

    private String name;

    private String description;

    private String location;

    private int latitude;

    private int longitude;

    private String country;

    private String state;

    private String city;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum Category {
        Historical, Adventure, Nature, Beach, City, Religious, Other
    }

}

