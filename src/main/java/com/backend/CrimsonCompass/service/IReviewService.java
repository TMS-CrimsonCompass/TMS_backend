package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.ReviewRequestDTO;
import com.backend.CrimsonCompass.dto.ReviewResponseDTO;

import java.util.List;

public interface IReviewService {
    void addReview(ReviewRequestDTO reviewRequestDTO);
    List<ReviewResponseDTO> getReviewsByEntity(Long entityId, String entityTypeName);
}
