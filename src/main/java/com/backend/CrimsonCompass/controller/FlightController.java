package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.FlightDTO;
import com.backend.CrimsonCompass.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<List<FlightDTO>> searchFlights(
            @RequestParam String from,
            @RequestParam String to) {
        List<FlightDTO> results = flightService.searchFlights(from, to);
        return ResponseEntity.ok(results);
    }
}