package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceImageRepository extends JpaRepository<PlaceImage, Integer> {

    List<PlaceImage> findByPlace_PlaceId(Integer placeId); // Fetch images for a specific place
}