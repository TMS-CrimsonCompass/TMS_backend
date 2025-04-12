package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.ItineraryDTO;
import com.backend.CrimsonCompass.dto.ItineraryResponseDTO;
import com.backend.CrimsonCompass.model.Itinerary;
import com.backend.CrimsonCompass.service.IItineraryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    @Autowired
    private IItineraryService itineraryService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping
    public Itinerary createItinerary(@RequestBody ItineraryDTO itineraryDTO, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Integer userId = (Integer) claims.get("userId");
            itineraryDTO.setUserId(userId);
        }
        return itineraryService.createItinerary(itineraryDTO);
    }

    @GetMapping("/user/{userId}")
    public List<Itinerary> getItinerariesByUserId(@PathVariable Integer userId) {
        return itineraryService.getItinerariesByUserId(userId);
    }

    @GetMapping("/master/{masterItineraryId}")
    public List<ItineraryResponseDTO> getItinerariesByMasterItineraryId(@PathVariable Integer masterItineraryId) {
        List<Itinerary> itineraries = itineraryService.getItinerariesByMasterItineraryId(masterItineraryId);
        return itineraries.stream().map(itinerary -> {
            ItineraryResponseDTO dto = new ItineraryResponseDTO();
            dto.setItineraryId(itinerary.getItineraryId());
            dto.setTitle(itinerary.getTitle());
            dto.setDescription(itinerary.getDescription());
            dto.setStartDate(itinerary.getStartDate());
            dto.setEndDate(itinerary.getEndDate());

            if (itinerary.getPlace() != null) {
                dto.setPlaceId(itinerary.getPlace().getPlaceId());
                dto.setPlaceName(itinerary.getPlace().getName());
                if (itinerary.getPlace().getImages() != null && !itinerary.getPlace().getImages().isEmpty()) {
                    dto.setPlaceImage(itinerary.getPlace().getImages().get(0).getImageUrl());
                }
            }

            if (itinerary.getHotel() != null) {
                dto.setHotelId(itinerary.getHotel().getHotelId());
                dto.setHotelName(itinerary.getHotel().getName());
                if (itinerary.getHotel().getImages() != null && !itinerary.getHotel().getImages().isEmpty()) {
                    dto.setHotelImage(itinerary.getHotel().getImages().get(0).getImageUrl());
                }
            }

            if (itinerary.getEntityType() != null) {
                dto.setEntityTypeName(itinerary.getEntityType().getEntityTypeName());
            }

            dto.setEntityId(itinerary.getEntityId());

            return dto;
        }).toList();
    }

    @PutMapping("/{itineraryId}")
    public Itinerary updateItinerary(@PathVariable Integer itineraryId, @RequestBody ItineraryDTO itineraryDTO) {
        return itineraryService.updateItinerary(itineraryId, itineraryDTO);
    }

    @DeleteMapping("/{itineraryId}")
    public ResponseEntity<Void> deleteItinerary(@PathVariable Integer itineraryId) {
        itineraryService.deleteItinerary(itineraryId);
        return ResponseEntity.noContent().build();
    }
}
