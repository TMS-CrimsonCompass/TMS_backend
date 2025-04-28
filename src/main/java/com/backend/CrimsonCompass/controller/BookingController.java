package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.BookingRequest;
import com.backend.CrimsonCompass.model.Booking;
import com.backend.CrimsonCompass.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request) {
        // 🔥 HARDCODE userId temporarily just for testing
        request.setUserId(1);  // 👈 this is just temporary (assume userId = 1 for now)

        return bookingService.createBooking(request);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUserId(@PathVariable Integer userId) {
        return bookingService.getBookingsByUserId(userId);
    }
}
