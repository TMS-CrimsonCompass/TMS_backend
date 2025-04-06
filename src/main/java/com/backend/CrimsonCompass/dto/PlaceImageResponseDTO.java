package com.backend.CrimsonCompass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceImageResponseDTO {
    private int imageId;
    private String imageUrl;

    public PlaceImageResponseDTO(int imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }
}