package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.UserItinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserItineraryRepository extends JpaRepository<UserItinerary, Integer> {
    List<UserItinerary> findByUserUserId(Integer userId);
    Optional<UserItinerary> findByUserItineraryId(Integer userItineraryId);
}
