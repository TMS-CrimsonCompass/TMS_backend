package com.backend.CrimsonCompass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private String airline;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String duration; // Optional: format as String if you want "12:30"
    private BigDecimal price;
    private int availableSeats;
}