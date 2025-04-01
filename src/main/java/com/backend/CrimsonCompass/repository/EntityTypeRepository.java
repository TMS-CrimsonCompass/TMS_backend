package com.backend.CrimsonCompass.repository;

import com.backend.CrimsonCompass.model.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntityTypeRepository extends JpaRepository<EntityType, Integer> {
    Optional<EntityType> findByEntityTypeName(String entityTypeName);
}
