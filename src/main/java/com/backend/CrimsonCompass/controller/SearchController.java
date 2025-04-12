package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.SearchSuggestionDTO;
import com.backend.CrimsonCompass.model.Hotel;
import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.repository.HotelRepository;
import com.backend.CrimsonCompass.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/autocomplete")
    public List<SearchSuggestionDTO> search(@RequestParam String query) {
        List<SearchSuggestionDTO> suggestions = new ArrayList<>();

        List<Place> places = placeRepository.searchPlaces(query);
        for (Place place : places) {
            SearchSuggestionDTO dto = new SearchSuggestionDTO();
            dto.setType("place");
            dto.setId(place.getPlaceId());
            dto.setName(place.getName());
            dto.setLocation(place.getLocation());
            dto.setCity(place.getCity());
            dto.setCountry(place.getCountry());
            if (place.getImages() != null && !place.getImages().isEmpty()) {
                dto.setImage(place.getImages().get(0).getImageUrl());
            }
            suggestions.add(dto);
        }

        List<Hotel> hotels = hotelRepository.searchHotels(query);
        for (Hotel hotel : hotels) {
            SearchSuggestionDTO dto = new SearchSuggestionDTO();
            dto.setType("hotel");
            dto.setId(hotel.getHotelId());
            dto.setName(hotel.getName());
            dto.setLocation(hotel.getLocation());
            dto.setCity(hotel.getCity());
            dto.setCountry(hotel.getCountry());
            if (hotel.getImages() != null && !hotel.getImages().isEmpty()) {
                dto.setImage(hotel.getImages().get(0).getImageUrl());
            }
            suggestions.add(dto);
        }

        return suggestions;
    }
}
