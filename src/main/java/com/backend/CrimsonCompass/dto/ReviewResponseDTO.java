package com.backend.CrimsonCompass.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {
    private Long reviewId;
    private String userName;
    private BigDecimal rating;
    private String reviewText;
    private LocalDateTime createdAt;
}
