package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.HotelRequestDTO;
import com.backend.CrimsonCompass.dto.HotelFilterRequestDTO;
import com.backend.CrimsonCompass.model.Hotel;
import com.backend.CrimsonCompass.model.Amenity;
import com.backend.CrimsonCompass.repository.HotelRepository;
import com.backend.CrimsonCompass.repository.HotelImageRepository;
import com.backend.CrimsonCompass.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;
    private final HotelImageRepository hotelImageRepository;
    private final AmenityRepository amenityRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, HotelImageRepository hotelImageRepository, AmenityRepository amenityRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelImageRepository = hotelImageRepository;
        this.amenityRepository = amenityRepository;
    }

    @Override
    public Hotel saveHotel(HotelRequestDTO hotelRequestDTO) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelRequestDTO.getName());
        hotel.setDescription(hotelRequestDTO.getDescription());
        hotel.setLocation(hotelRequestDTO.getLocation());
        hotel.setLatitude(hotelRequestDTO.getLatitude());
        hotel.setLongitude(hotelRequestDTO.getLongitude());
        hotel.setCountry(hotelRequestDTO.getCountry());
        hotel.setState(hotelRequestDTO.getState());
        hotel.setCity(hotelRequestDTO.getCity());
        hotel.setPricePerNight(hotelRequestDTO.getPricePerNight());
        hotel.setRating(hotelRequestDTO.getRating());
        List<String> amenityNames = hotelRequestDTO.getAmenities();
        Set<Amenity> amenities = new HashSet<>();
        if (amenityNames != null) {
            for (String name : amenityNames) {
                Amenity amenity = amenityRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Amenity not found: " + name));
                amenities.add(amenity);
            }
        }
        hotel.setAmenities(amenities);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(Integer hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + hotelId));
    }

    @Override
    public List<Hotel> filterHotels(HotelFilterRequestDTO filterDTO) {
        String country = filterDTO.getCountry();
        String state = filterDTO.getState();
        String city = filterDTO.getCity();
        BigDecimal minPrice = filterDTO.getMinPrice();
        BigDecimal maxPrice = filterDTO.getMaxPrice();
        BigDecimal minRating = filterDTO.getMinRating();

        return hotelRepository.filterHotels(country, state, city, minPrice, maxPrice, minRating);
    }
}
