package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.model.Place;
import com.backend.CrimsonCompass.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceService placeService;

    @Test
    public void testGetPlaceById_ReturnsPlace() {
        Integer placeId = 1;
        Place place = new Place();
        place.setPlaceId(placeId);

        // Stub the repository to return a non-empty Optional.
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));

        Optional<Place> result = placeService.getPlaceById(placeId);

        // Assert that the Optional is present and contains the expected Place.
        assertTrue(result.isPresent());
        assertEquals(placeId, result.get().getPlaceId());
    }
}