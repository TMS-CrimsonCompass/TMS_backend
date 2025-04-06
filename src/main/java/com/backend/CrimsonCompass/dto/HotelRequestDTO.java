package com.backend.CrimsonCompass.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HotelRequestDTO {
    private String name;
    private String description;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String country;
    private String state;
    private String city;
    private BigDecimal pricePerNight;
    private BigDecimal rating;
    private List<String> amenities;
}
