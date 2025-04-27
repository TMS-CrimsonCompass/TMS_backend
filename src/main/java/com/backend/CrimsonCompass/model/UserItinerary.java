package com.backend.CrimsonCompass.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class UserItinerary {


    private Integer userItineraryId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masterItineraryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

}
