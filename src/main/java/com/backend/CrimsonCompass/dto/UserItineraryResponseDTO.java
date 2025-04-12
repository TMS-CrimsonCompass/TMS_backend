package com.backend.CrimsonCompass.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserItineraryResponseDTO {
    private Integer masterItineraryId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
