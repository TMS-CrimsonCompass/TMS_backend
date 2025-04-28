package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.PlaceRequestDTO;
import com.backend.CrimsonCompass.dto.PlaceResponseDTO;
import com.backend.CrimsonCompass.dto.PlaceImageResponseDTO;
import com.backend.CrimsonCompass.dto.ReviewResponseDTO; // Import ReviewResponseDTO
import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.model.PlaceImage;
import com.backend.CrimsonCompass.model.PlaceCategory; // Import PlaceCategory
import com.backend.CrimsonCompass.service.IPlaceService;
import com.backend.CrimsonCompass.service.IReviewService; // Import IReviewService
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
    private final IReviewService reviewService; // New field for IReviewService

    @Autowired
    public PlaceController(IPlaceService placeService, IReviewService reviewService) { // Updated constructor
        this.placeService = placeService;
        this.reviewService = reviewService;
    }

    // GET /api/places/search?q=La
    @GetMapping("/search")
    public ResponseEntity<List<PlaceResponseDTO>> searchPlaces(@RequestParam("q") String query) {
        List<PlaceResponseDTO> responseDTOs = placeService.searchPlaces(query);
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
        PlaceCategory category = new PlaceCategory(); // Create PlaceCategory instance
        category.setName(placeRequest.getCategory()); // assuming name is passed instead of ID
        place.setCategory(category); // Set the category

        Place savedPlace = placeService.savePlace(place);
        return ResponseEntity.ok(convertToResponseDTO(savedPlace));
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponseDTO>> getAllPlaces() {
        List<Place> places = placeService.getAllPlaces();
        List<PlaceResponseDTO> response = places.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PlaceResponseDTO>> getPlacesByCategory(@PathVariable String category) {
        List<Place> places = placeService.getPlacesByCategory(category);
        List<PlaceResponseDTO> response = places.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
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
                .map(image -> {
                    String url = image.getImageUrl();
                    if (!url.startsWith("http")) {
                        url = "http://localhost:8080/uploads/images/" + url;
                    }
                    return new PlaceImageResponseDTO(image.getImageId(), url);
                })
                .collect(Collectors.toList());

        List<ReviewResponseDTO> reviews = reviewService.getReviewsByEntity((long) place.getPlaceId(), "Place"); // New block to get reviews

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
                place.getCategory().getName(), // Updated line
                imageResponses, // Include images here
                reviews // Include reviews here
        );
    }

}
