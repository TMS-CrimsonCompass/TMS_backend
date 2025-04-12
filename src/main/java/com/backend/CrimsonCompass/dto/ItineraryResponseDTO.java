package com.backend.CrimsonCompass.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ItineraryResponseDTO {
    private Integer itineraryId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    private Integer placeId;
    private String placeName;
    private String placeImage;

    private Integer hotelId;
    private String hotelName;
    private String hotelImage;

    private String entityTypeName;
    private Integer entityId;
}
