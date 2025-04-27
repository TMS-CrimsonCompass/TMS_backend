package com.backend.CrimsonCompass.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Integer userId;
    private Integer entityId;
    private Integer entityTypeId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Double totalPrice;
}
