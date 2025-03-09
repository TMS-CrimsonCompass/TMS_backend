package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.PlaceImageResponseDTO;
import com.backend.CrimsonCompass.dto.PlaceResponseDTO;
import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.model.PlaceImage;
import com.backend.CrimsonCompass.repository.PlaceImageRepository;
import com.backend.CrimsonCompass.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService implements IPlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceImageRepository placeImageRepository;

    public PlaceService(PlaceRepository placeRepository, PlaceImageRepository placeImageRepository) {
        this.placeRepository = placeRepository;
        this.placeImageRepository = placeImageRepository;
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
    public List<Place> getPlacesByCategory(String category) {
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
}