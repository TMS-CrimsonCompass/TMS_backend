package com.backend.CrimsonCompass.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import com.backend.CrimsonCompass.dto.ReviewResponseDTO;

@Getter
@Setter
public class PlaceResponseDTO {
    private int placeId;
    private String name;
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;
    private String country;
    private String state;
    private String city;
    private String category;
    private List<PlaceImageResponseDTO> images;
    private List<ReviewResponseDTO> reviews;

    public PlaceResponseDTO(int placeId, String name, String description, String location,
                            Double latitude, Double longitude, String country, String state,
                            String city, String category, List<PlaceImageResponseDTO> images,
                            List<ReviewResponseDTO> reviews) {
        this.placeId = placeId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.state = state;
        this.city = city;
        this.category = category;
        this.images = images;
        this.reviews = reviews;
    }
}