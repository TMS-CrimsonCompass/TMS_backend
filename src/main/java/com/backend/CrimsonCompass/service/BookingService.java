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
    private final EmailService emailService;
    private final UserRepository userRepository;

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

        User user= userRepository.findByUserId(request.getUserId());
        String subject = "Booking Confirmation - " + entityType.getEntityTypeName();

        String body = "Dear " + user.getFirstName() + ",\n\n" +
                "Thank you for choosing our service. Here are your booking details:\n\n" +
                "Booking ID: " + savedBooking.getBookingId() + "\n" +
                (savedBooking.getCheckInDate() != null ? "Check-In Date: " + savedBooking.getCheckInDate() + "\n" : "") +
                (savedBooking.getCheckOutDate() != null ? "Check-Out Date: " + savedBooking.getCheckOutDate() + "\n" : "") +
                (savedBooking.getDepartureDate() != null ? "Departure Date: " + savedBooking.getDepartureDate() + "\n" : "") +
                (savedBooking.getArrivalDate() != null ? "Arrival Date: " + savedBooking.getArrivalDate() + "\n" : "") +
                "Total Price: $" + savedBooking.getTotalPrice() + "\n" +
                "Booking Status: " + savedBooking.getStatus().name() + "\n\n" +
                "If you have any questions, feel free to contact our support team.\n\n" +
                "Best regards,\n" +
                "The Bookings Team - Crimson Compass";
        emailService.sendEmail(user.getEmail(),subject,body);

        return savedBooking;
    }

    public List<Booking> getBookingsByUserId(Integer userId) {
        return bookingRepository.findByUserId(userId);
    }
}
