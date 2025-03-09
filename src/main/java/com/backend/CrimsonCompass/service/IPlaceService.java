package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.model.PlaceImage;

import java.util.List;
import java.util.Optional;

public interface IPlaceService {
    Optional<Place> getPlaceById(Integer placeId);
    List<PlaceImage> getImagesByPlaceId(Integer placeId);
    Place savePlace(Place place);
    List<Place> getPlacesByCategory(String category);
    List<Place> getPlacesByCity(String city);
    List<Place> getPlacesByCountry(String country);
}