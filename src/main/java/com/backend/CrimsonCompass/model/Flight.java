package com.backend.CrimsonCompass.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Flights")
@Getter
@Setter
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long id;

    private String airline;

    @Column(name = "flight_number", unique = true)
    private String flightNumber;

    @Column(name = "departure_airport")
    private String departureAirport;

    @Column(name = "arrival_airport")
    private String arrivalAirport;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "arrival_city")
    private String arrivalCity;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    private Time duration;

    private BigDecimal price;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;
}