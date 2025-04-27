package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Original basic search
    List<Flight> findByDepartureCityContainingIgnoreCaseAndArrivalCityContainingIgnoreCase(String from, String to);

    // ✅ New method: includes date range filter (departure_time between)
    List<Flight> findByDepartureCityContainingIgnoreCaseAndArrivalCityContainingIgnoreCaseAndDepartureTimeBetween(
            String from,
            String to,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );
}
