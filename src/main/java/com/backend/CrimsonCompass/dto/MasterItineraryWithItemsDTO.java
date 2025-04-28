package com.backend.CrimsonCompass.dto;

import lombok.Data;
import java.util.List;

@Data
public class MasterItineraryWithItemsDTO {
    private String masterTitle;
    private String masterDescription;
    private List<ItineraryResponseDTO> itineraries;
}