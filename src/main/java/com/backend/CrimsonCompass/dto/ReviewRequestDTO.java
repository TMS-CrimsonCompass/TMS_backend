package com.backend.CrimsonCompass.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReviewRequestDTO {
    private Long userId;
    private Long entityId;
    private String entityTypeName;
    private BigDecimal rating;
    private String reviewText;
}
