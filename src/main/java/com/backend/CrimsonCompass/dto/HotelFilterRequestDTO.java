package com.backend.CrimsonCompass.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HotelFilterRequestDTO {
    private String country;
    private String state;
    private String city;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal minRating;
}
