package com.backend.CrimsonCompass.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    private double latitude;

    private double longitude;

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

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaceImage> images = new ArrayList<>();

    public Place() {
    }

}

