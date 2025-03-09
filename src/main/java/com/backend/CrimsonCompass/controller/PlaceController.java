package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.PlaceRequestDTO;
import com.backend.CrimsonCompass.dto.PlaceResponseDTO;
import com.backend.CrimsonCompass.dto.PlaceImageResponseDTO;
import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.model.PlaceImage;
import com.backend.CrimsonCompass.service.IPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/places")
@CrossOrigin("http://localhost:3000")
public class PlaceController {

    private final IPlaceService placeService;

    @Autowired
    public PlaceController(IPlaceService placeService) {
        this.placeService = placeService;
    }

    // GET /api/places/search?q=La
    @GetMapping("/search")
    public ResponseEntity<List<PlaceResponseDTO>> searchPlaces(@RequestParam("q") String query) {
        List<PlaceResponseDTO> responseDTOs = placeService.searchPlaces(query); // Delegate to service
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceResponseDTO> getPlaceById(@PathVariable Integer placeId) {
        return placeService.getPlaceById(placeId)
                .map(place -> ResponseEntity.ok(convertToResponseDTO(place)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{placeId}/images")
    public ResponseEntity<List<PlaceImageResponseDTO>> getImagesByPlaceId(@PathVariable Integer placeId) {
        List<PlaceImage> images = placeService.getImagesByPlaceId(placeId);
        List<PlaceImageResponseDTO> response = images.stream()
                .map(image -> new PlaceImageResponseDTO(image.getImageId(), image.getImageUrl()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PlaceResponseDTO> savePlace(@RequestBody PlaceRequestDTO placeRequest) {
        Place place = new Place();
        place.setName(placeRequest.getName());
        place.setDescription(placeRequest.getDescription());
        place.setLocation(placeRequest.getLocation());
        place.setLatitude(placeRequest.getLatitude().intValue());
        place.setLongitude(placeRequest.getLongitude().intValue());
        place.setCountry(placeRequest.getCountry());
        place.setState(placeRequest.getState());
        place.setCity(placeRequest.getCity());
        place.setCategory(Place.Category.valueOf(placeRequest.getCategory()));

        Place savedPlace = placeService.savePlace(place);
        return ResponseEntity.ok(convertToResponseDTO(savedPlace));
    }



    @GetMapping("/category/{category}")
    public ResponseEntity<List<PlaceResponseDTO>> getPlacesByCategory(@PathVariable String category) {
        try {
            // Convert string to enum
            Place.Category categoryEnum = Place.Category.valueOf(category);

            // Fetch places by category
            List<Place> places = placeService.getPlacesByCategory(categoryEnum.name());
            List<PlaceResponseDTO> response = places.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Handle invalid category value
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<PlaceResponseDTO>> getPlacesByCity(@PathVariable String city) {
        List<Place> places = placeService.getPlacesByCity(city);
        List<PlaceResponseDTO> response = places.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<PlaceResponseDTO>> getPlacesByCountry(@PathVariable String country) {
        List<Place> places = placeService.getPlacesByCountry(country);
        List<PlaceResponseDTO> response = places.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private PlaceResponseDTO convertToResponseDTO(Place place) {
        List<PlaceImage> images = placeService.getImagesByPlaceId(place.getPlaceId());
        List<PlaceImageResponseDTO> imageResponses = images.stream()
                .map(image -> new PlaceImageResponseDTO(image.getImageId(), image.getImageUrl()))
                .collect(Collectors.toList());

        return new PlaceResponseDTO(
                place.getPlaceId(),
                place.getName(),
                place.getDescription(),
                place.getLocation(),
                (double) place.getLatitude(),
                (double) place.getLongitude(),
                place.getCountry(),
                place.getState(),
                place.getCity(),
                place.getCategory().name(),
                imageResponses // Include images here
        );
    }

}
