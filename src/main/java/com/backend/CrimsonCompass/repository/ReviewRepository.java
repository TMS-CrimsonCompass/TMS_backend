package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.Review;
import com.backend.CrimsonCompass.model.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEntityIdAndEntityType(Long entityId, EntityType entityType);
}
