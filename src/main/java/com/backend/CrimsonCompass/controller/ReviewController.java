package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.ReviewRequestDTO;
import com.backend.CrimsonCompass.dto.ReviewResponseDTO;
import com.backend.CrimsonCompass.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final IReviewService reviewService;

    @Autowired
    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public void addReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.addReview(reviewRequestDTO);
    }

    @GetMapping("/{entityType}/{entityId}")
    public List<ReviewResponseDTO> getReviewsByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        return reviewService.getReviewsByEntity(entityId, entityType);
    }
}
