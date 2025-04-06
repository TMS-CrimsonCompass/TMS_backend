package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

  @Query("SELECT h FROM Hotel h WHERE " +
         "(:country IS NULL OR h.country = :country) AND " +
         "(:state IS NULL OR h.state = :state) AND " +
         "(:city IS NULL OR h.city = :city) AND " +
         "(:minPrice IS NULL OR h.pricePerNight >= :minPrice) AND " +
         "(:maxPrice IS NULL OR h.pricePerNight <= :maxPrice) AND " +
         "(:minRating IS NULL OR h.rating >= :minRating)")
  List<Hotel> filterHotels(
      @Param("country") String country,
      @Param("state") String state,
      @Param("city") String city,
      @Param("minPrice") BigDecimal minPrice,
      @Param("maxPrice") BigDecimal maxPrice,
      @Param("minRating") BigDecimal minRating
  );
}
