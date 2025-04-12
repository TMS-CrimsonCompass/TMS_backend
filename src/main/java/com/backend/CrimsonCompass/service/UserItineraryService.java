package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.UserItineraryDTO;
import com.backend.CrimsonCompass.model.User;
import com.backend.CrimsonCompass.model.UserItinerary;
import com.backend.CrimsonCompass.repository.UserItineraryRepository;
import com.backend.CrimsonCompass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserItineraryService implements IUserItineraryService {

    @Autowired
    private UserItineraryRepository userItineraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserItinerary addUserItinerary(UserItineraryDTO dto) {
        UserItinerary userItinerary = new UserItinerary();
        User user = userRepository.findById(Long.valueOf(dto.getUserId())).orElse(null);

        userItinerary.setUser(user);
        userItinerary.setStartDate(dto.getStartDate());
        userItinerary.setEndDate(dto.getEndDate());
        userItinerary.setTitle(dto.getTitle());
        userItinerary.setDescription(dto.getDescription());

        return userItineraryRepository.save(userItinerary);
    }

    @Override
    public List<UserItinerary> getUserItineraries(Integer userId) {
        return userItineraryRepository.findByUserUserId(userId);
    }

    @Override
    public UserItinerary getUserItineraryById(Integer userItineraryId) {
        return userItineraryRepository.findByUserItineraryId(userItineraryId).orElse(null);
    }
}
