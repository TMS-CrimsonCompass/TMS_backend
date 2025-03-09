package com.backend.CrimsonCompass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceRequestDTO {
    private String name;
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;
    private String country;
    private String state;
    private String city;
    private String category;
}