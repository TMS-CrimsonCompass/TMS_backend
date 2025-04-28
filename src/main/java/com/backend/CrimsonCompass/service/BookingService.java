package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.BookingRequest;
import com.backend.CrimsonCompass.model.*;
import com.backend.CrimsonCompass.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EntityTypeRepository entityTypeRepository;

    public Booking createBooking(BookingRequest request) {
        // Get EntityType (e.g., Flight, Hotel, etc.)
        EntityType entityType = entityTypeRepository.findById(request.getEntityTypeId())
                .orElseThrow(() -> new RuntimeException("Entity Type not found"));

        // Create booking
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setEntityId(request.getEntityId());
        booking.setEntityType(entityType);
        booking.setBookingDate(LocalDateTime.now());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setDepartureDate(request.getDepartureDate());
        booking.setArrivalDate(request.getArrivalDate());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);



        return savedBooking;
    }

    public List<Booking> getBookingsByUserId(Integer userId) {
        return bookingRepository.findByUserId(userId);
    }
}
