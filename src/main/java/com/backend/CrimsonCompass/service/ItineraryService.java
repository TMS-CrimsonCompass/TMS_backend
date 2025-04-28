package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.ItineraryDTO;
import com.backend.CrimsonCompass.model.*;
import com.backend.CrimsonCompass.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraryService implements IItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private UserItineraryRepository userItineraryRepository;

    //@Autowired
    //private BookingRepository bookingRepository;

    @Override
    public Itinerary createItinerary(ItineraryDTO dto) {
        Itinerary itinerary = new Itinerary();
        itinerary.setTitle(dto.getTitle());
        itinerary.setDescription(dto.getDescription());
        itinerary.setStartDate(dto.getStartDate());
        itinerary.setEndDate(dto.getEndDate());

        itinerary.setUser(userRepository.findById(Long.valueOf(dto.getUserId())).orElse(null));
        if (dto.getPlaceId() != null) {
            itinerary.setPlace(placeRepository.findById(dto.getPlaceId()).orElse(null));
        }
        if (dto.getHotelId() != null) {
            itinerary.setHotel(hotelRepository.findById(dto.getHotelId()).orElse(null));
        }
        if (dto.getEntityTypeId() != null) {
            itinerary.setEntityType(entityTypeRepository.findById(dto.getEntityTypeId()).orElse(null));
        }
        itinerary.setEntityId(dto.getEntityId());
        if (dto.getMasterItineraryId() != null) {
            UserItinerary userItinerary = userItineraryRepository.findById(dto.getMasterItineraryId()).orElse(null);
            if (userItinerary == null) {
                userItinerary = new UserItinerary();
                userItinerary.setUser(userRepository.findById(Long.valueOf(dto.getUserId())).orElse(null));
                userItinerary.setTitle(dto.getTitle());
                userItinerary.setDescription(dto.getDescription());
                userItinerary.setStartDate(dto.getStartDate());
                userItinerary.setEndDate(dto.getEndDate());
                userItinerary.setMasterItineraryId(dto.getMasterItineraryId());
                userItinerary = userItineraryRepository.save(userItinerary);
            }
            itinerary.setMasterItinerary(userItinerary);
        }
        //if (dto.getBookingId() != null) {
        //    itinerary.setBooking(bookingRepository.findById(dto.getBookingId()).orElse(null));
        //}

        return itineraryRepository.save(itinerary);
    }

    @Override
    public List<Itinerary> getItinerariesByUserId(Integer userId) {
        return itineraryRepository.findByUserUserId(userId);
    }

    @Override
    public List<Itinerary> getItinerariesByMasterItineraryId(Integer masterItineraryId) {
        return itineraryRepository.findByMasterItineraryMasterItineraryId(masterItineraryId);
    }

    @Override
    public void deleteItinerary(Integer itineraryId) {
        if (!itineraryRepository.existsById(itineraryId)) {
            throw new RuntimeException("Itinerary with ID " + itineraryId + " does not exist.");
        }
        itineraryRepository.deleteById(itineraryId);
    }

    @Override
    public Itinerary updateItinerary(Integer itineraryId, ItineraryDTO dto) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId).orElseThrow(() ->
            new RuntimeException("Itinerary not found"));

        itinerary.setTitle(dto.getTitle());
        itinerary.setDescription(dto.getDescription());
        itinerary.setStartDate(dto.getStartDate());
        itinerary.setEndDate(dto.getEndDate());

        if (dto.getPlaceId() != null) {
            Place place = placeRepository.findById(dto.getPlaceId()).orElse(null);
            itinerary.setPlace(place);
        }

        if (dto.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(dto.getHotelId()).orElse(null);
            itinerary.setHotel(hotel);
        }

        if (dto.getEntityTypeId() != null) {
            EntityType entityType = entityTypeRepository.findById(dto.getEntityTypeId()).orElse(null);
            itinerary.setEntityType(entityType);
        }

        itinerary.setEntityId(dto.getEntityId());

        return itineraryRepository.save(itinerary);
    }
}
