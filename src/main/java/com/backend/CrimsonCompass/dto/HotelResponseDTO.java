package com.backend.CrimsonCompass.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import com.backend.CrimsonCompass.dto.AmenityDTO;

@Getter
@Setter
public class HotelResponseDTO {
    private Integer hotelId;
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
    private List<AmenityDTO> amenities;
    private List<String> imageUrls;
}
