package com.backend.CrimsonCompass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceSearchRequestDTO {
    private String category;
    private String city;
    private String country;
}