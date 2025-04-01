package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, Integer> {
    List<HotelImage> findByHotelHotelId(Integer hotelId);
}
