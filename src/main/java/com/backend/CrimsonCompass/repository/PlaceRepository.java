package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.model.PlaceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    Optional<Place> findById(Integer placeId); // Find a place by ID

    List<Place> findByCategory(PlaceCategory category); // Find places by category

    List<Place> findByCity(String city); // Find places by city

    List<Place> findByCountry(String country); // Find places by country

    @Query("SELECT DISTINCT p FROM Place p LEFT JOIN FETCH p.images WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.location) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.country) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.city) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Place> searchPlaces(@Param("query") String query);
}