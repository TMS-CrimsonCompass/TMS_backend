package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.ItineraryDTO;
import com.backend.CrimsonCompass.model.Itinerary;

import java.util.List;

public interface IItineraryService {
    Itinerary createItinerary(ItineraryDTO dto);
    List<Itinerary> getItinerariesByUserId(Integer userId);
    List<Itinerary> getItinerariesByMasterItineraryId(Integer masterItineraryId);
    Itinerary updateItinerary(Integer itineraryId, ItineraryDTO dto);
    void deleteItinerary(Integer itineraryId);
}
