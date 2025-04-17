package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.FlightDTO;
import com.backend.CrimsonCompass.model.Flight;
import com.backend.CrimsonCompass.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<FlightDTO> searchFlights(String from, String to) {
        return flightRepository
                .findByDepartureCityContainingIgnoreCaseAndArrivalCityContainingIgnoreCase(from, to)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FlightDTO convertToDTO(Flight flight) {
        return new FlightDTO(
                flight.getAirline(),
                flight.getFlightNumber(),
                flight.getDepartureCity(),
                flight.getArrivalCity(),
                flight.getDepartureAirport(),
                flight.getArrivalAirport(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getDuration().toString(),  // Converts SQL TIME to "HH:mm:ss"
                flight.getPrice(),
                flight.getAvailableSeats()
        );
    }
}