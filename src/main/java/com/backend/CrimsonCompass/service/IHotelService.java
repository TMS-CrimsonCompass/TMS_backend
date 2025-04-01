package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.HotelRequestDTO;
import com.backend.CrimsonCompass.dto.HotelFilterRequestDTO;
import com.backend.CrimsonCompass.model.Hotel;

import java.util.List;

public interface IHotelService {
    Hotel saveHotel(HotelRequestDTO hotelRequestDTO);
    List<Hotel> getAllHotels();
    Hotel getHotelById(Integer hotelId);
    List<Hotel> filterHotels(HotelFilterRequestDTO filterDTO);
}
