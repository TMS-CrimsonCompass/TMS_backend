package com.backend.CrimsonCompass.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.backend.CrimsonCompass.model.UserItinerary; // Import added

// import com.backend.CrimsonCompass.model.Booking; // Commented out as Booking class doesn't exist yet

@Entity
@Data
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itineraryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "entity_type_id")
    private EntityType entityType;

    private Integer entityId;

    //@ManyToOne
    //@JoinColumn(name = "booking_id")
    //private Booking booking; // Commented out since Booking class doesn't exist yet

    @ManyToOne
    @JoinColumn(name = "master_itinerary_id") // New field added
    private UserItinerary masterItinerary; // New field added
}
