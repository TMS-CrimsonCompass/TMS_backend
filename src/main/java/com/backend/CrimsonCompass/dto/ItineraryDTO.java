package com.backend.CrimsonCompass.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ItineraryDTO {
    private Integer itineraryId;
    private Integer userId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer placeId;
    private Integer hotelId;
    private Integer entityTypeId;
    private Integer entityId;
    private Integer bookingId;
    private Integer masterItineraryId;
}
