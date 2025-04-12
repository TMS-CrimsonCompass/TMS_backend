package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.UserItineraryDTO;
import com.backend.CrimsonCompass.dto.UserItineraryResponseDTO;
import com.backend.CrimsonCompass.model.UserItinerary;
import com.backend.CrimsonCompass.service.IUserItineraryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-itineraries")
public class UserItineraryController {

    @Autowired
    private IUserItineraryService userItineraryService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping
    public UserItinerary addUserItinerary(@RequestBody UserItineraryDTO dto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Integer userId = (Integer) claims.get("userId");
            dto.setUserId(userId);
        }
        return userItineraryService.addUserItinerary(dto);
    }

    @GetMapping
    public List<UserItineraryResponseDTO> getUserItineraries(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Integer userId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            userId = (Integer) claims.get("userId");
        }

        List<UserItinerary> userItineraries = userItineraryService.getUserItineraries(userId);

        return userItineraries.stream().map(itinerary -> {
            UserItineraryResponseDTO dto = new UserItineraryResponseDTO();
            dto.setMasterItineraryId(itinerary.getMasterItineraryId());
            dto.setTitle(itinerary.getTitle());
            dto.setDescription(itinerary.getDescription());
            dto.setStartDate(itinerary.getStartDate());
            dto.setEndDate(itinerary.getEndDate());
            return dto;
        }).toList();
    }
}
