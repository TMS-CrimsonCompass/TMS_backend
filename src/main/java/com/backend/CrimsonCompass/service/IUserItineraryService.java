package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.UserItineraryDTO;
import com.backend.CrimsonCompass.model.UserItinerary;

import java.util.List;

public interface IUserItineraryService {
    UserItinerary addUserItinerary(UserItineraryDTO dto);
    List<UserItinerary> getUserItineraries(Integer userId);
    UserItinerary getUserItineraryById(Integer userItineraryId);
}
