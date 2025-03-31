package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.PlaceImageResponseDTO;
import com.backend.CrimsonCompass.dto.PlaceResponseDTO;
import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.model.PlaceImage;
import com.backend.CrimsonCompass.repository.PlaceImageRepository;
import com.backend.CrimsonCompass.repository.PlaceRepository;
import com.backend.CrimsonCompass.repository.PlaceCategoryRepository;
import com.backend.CrimsonCompass.model.PlaceCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService implements IPlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceImageRepository placeImageRepository;
    private final PlaceCategoryRepository placeCategoryRepository;

    public PlaceService(PlaceRepository placeRepository, PlaceImageRepository placeImageRepository, PlaceCategoryRepository placeCategoryRepository) {
        this.placeRepository = placeRepository;
        this.placeImageRepository = placeImageRepository;
        this.placeCategoryRepository = placeCategoryRepository;
    }


    @Override
    public Optional<Place> getPlaceById(Integer placeId) {
        return placeRepository.findById(placeId);
    }

    @Override
    public List<PlaceImage> getImagesByPlaceId(Integer placeId) {
        return placeImageRepository.findByPlace_PlaceId(placeId);
    }

    @Override
    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public List<Place> getPlacesByCategory(String categoryName) {
        PlaceCategory category = placeCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryName));
        return placeRepository.findByCategory(category);
    }

    @Override
    public List<Place> getPlacesByCity(String city) {
        return placeRepository.findByCity(city);
    }

    @Override
    public List<Place> getPlacesByCountry(String country) {
        return placeRepository.findByCountry(country);
    }

    @Override
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public List<PlaceResponseDTO> searchPlaces(String query) {
        List<Place> places = placeRepository.searchPlaces(query);

        return places.stream()
                .map(place -> new PlaceResponseDTO(
                        place.getPlaceId(),
                        place.getName(),
                        place.getDescription(),
                        place.getLocation(),
                        place.getLatitude(),
                        place.getLongitude(),
                        place.getCountry(),
                        place.getState(),
                        place.getCity(),
                        place.getCategory().getName(),
                        // Properly arrange parentheses for nested stream operations
                        place.getImages().stream()
                                .map(img -> new PlaceImageResponseDTO(
                                        img.getImageId(),
                                        img.getImageUrl()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }


}