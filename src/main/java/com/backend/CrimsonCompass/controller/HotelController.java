package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.HotelRequestDTO;
import com.backend.CrimsonCompass.dto.HotelResponseDTO;
import com.backend.CrimsonCompass.dto.HotelFilterRequestDTO;
import com.backend.CrimsonCompass.dto.AmenityDTO;
import com.backend.CrimsonCompass.dto.ReviewResponseDTO;
import com.backend.CrimsonCompass.model.Hotel;
import com.backend.CrimsonCompass.model.HotelImage;
import com.backend.CrimsonCompass.model.Amenity;
import com.backend.CrimsonCompass.service.IHotelService;
import com.backend.CrimsonCompass.service.IReviewService;
import com.backend.CrimsonCompass.repository.HotelImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final IHotelService hotelService;
    private final HotelImageRepository hotelImageRepository;
    private final IReviewService reviewService;

    @Autowired
    public HotelController(IHotelService hotelService, HotelImageRepository hotelImageRepository, IReviewService reviewService) {
        this.hotelService = hotelService;
        this.hotelImageRepository = hotelImageRepository;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<HotelResponseDTO> saveHotel(@RequestBody HotelRequestDTO hotelRequestDTO) {
        Hotel savedHotel = hotelService.saveHotel(hotelRequestDTO);
        HotelResponseDTO response = convertToResponseDTO(savedHotel);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        List<HotelResponseDTO> responseList = hotels.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> getHotelById(@PathVariable Integer id) {
        Hotel hotel = hotelService.getHotelById(id);
        HotelResponseDTO response = convertToResponseDTO(hotel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<HotelResponseDTO>> filterHotels(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal minRating) {

        HotelFilterRequestDTO filterDTO = new HotelFilterRequestDTO();
        filterDTO.setCountry(country);
        filterDTO.setState(state);
        filterDTO.setCity(city);
        filterDTO.setMinPrice(minPrice);
        filterDTO.setMaxPrice(maxPrice);
        filterDTO.setMinRating(minRating);

        List<Hotel> filteredHotels = hotelService.filterHotels(filterDTO);
        List<HotelResponseDTO> responseList = filteredHotels.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    private HotelResponseDTO convertToResponseDTO(Hotel hotel) {
        HotelResponseDTO dto = new HotelResponseDTO();
        dto.setHotelId(hotel.getHotelId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setLocation(hotel.getLocation());
        dto.setLatitude(hotel.getLatitude());
        dto.setLongitude(hotel.getLongitude());
        dto.setCountry(hotel.getCountry());
        dto.setState(hotel.getState());
        dto.setCity(hotel.getCity());
        dto.setPricePerNight(hotel.getPricePerNight());
        dto.setRating(hotel.getRating());

        List<AmenityDTO> amenityDTOs = hotel.getAmenities().stream().map(amenity -> {
            AmenityDTO a = new AmenityDTO();
            a.setName(amenity.getName());
            a.setIconPath(amenity.getIconPath());
            return a;
        }).collect(Collectors.toList());
        dto.setAmenities(amenityDTOs);

        List<String> imageUrls = hotelImageRepository.findByHotelHotelId(hotel.getHotelId())
                .stream()
                .map(HotelImage::getImageUrl)
                .collect(Collectors.toList());
        dto.setImageUrls(imageUrls);

        List<ReviewResponseDTO> reviews = reviewService.getReviewsByEntity(hotel.getHotelId().longValue(), "Hotel");
        dto.setReviews(reviews);

        return dto;
    }
}
