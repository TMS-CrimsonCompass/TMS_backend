package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.ReviewRequestDTO;
import com.backend.CrimsonCompass.dto.ReviewResponseDTO;
import com.backend.CrimsonCompass.model.EntityType;
import com.backend.CrimsonCompass.model.Review;
import com.backend.CrimsonCompass.model.User;
import com.backend.CrimsonCompass.repository.EntityTypeRepository;
import com.backend.CrimsonCompass.repository.ReviewRepository;
import com.backend.CrimsonCompass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final EntityTypeRepository entityTypeRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, EntityTypeRepository entityTypeRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.entityTypeRepository = entityTypeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addReview(ReviewRequestDTO dto) {
        Review review = new Review();
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));
        EntityType entityType = entityTypeRepository.findByEntityTypeName(dto.getEntityTypeName())
                .orElseThrow(() -> new RuntimeException("EntityType not found: " + dto.getEntityTypeName()));

        review.setUser(user);
        review.setEntityId(dto.getEntityId());
        review.setEntityType(entityType);
        review.setRating(dto.getRating());
        review.setReviewText(dto.getReviewText());

        reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByEntity(Long entityId, String entityTypeName) {
        EntityType entityType = entityTypeRepository.findByEntityTypeName(entityTypeName)
                .orElseThrow(() -> new RuntimeException("EntityType not found: " + entityTypeName));

        return reviewRepository.findByEntityIdAndEntityType(entityId, entityType)
                .stream()
                .map(review -> {
                    ReviewResponseDTO dto = new ReviewResponseDTO();
                    dto.setReviewId(review.getReviewId());
                    dto.setUserName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
                    dto.setRating(review.getRating());
                    dto.setReviewText(review.getReviewText());
                    dto.setCreatedAt(review.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
