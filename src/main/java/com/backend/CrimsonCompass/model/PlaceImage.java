package com.backend.CrimsonCompass.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.LocalDateTime;


@Entity
@Table(name = "Place_Images")
@Getter
@Setter
public class PlaceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int imageId;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false) // Foreign key reference
    private Place place;

    private String imageUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}
