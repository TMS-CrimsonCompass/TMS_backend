package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
    List<Itinerary> findByUserUserId(Integer userId);
    List<Itinerary> findByMasterItineraryUserItineraryId(Integer masterItineraryId);
}
