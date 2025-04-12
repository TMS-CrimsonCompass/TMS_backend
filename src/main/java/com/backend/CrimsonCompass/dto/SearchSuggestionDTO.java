package com.backend.CrimsonCompass.dto;

import lombok.Data;

@Data
public class SearchSuggestionDTO {
    private String type;
    private Integer id;
    private String name;
    private String location;
    private String city;
    private String country;
    private String image;
}
