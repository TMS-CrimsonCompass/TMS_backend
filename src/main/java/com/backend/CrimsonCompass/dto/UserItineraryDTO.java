package com.backend.CrimsonCompass.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserItineraryDTO {
    private Integer userItineraryId;
    private Integer userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;
}
