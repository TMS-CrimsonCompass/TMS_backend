package com.backend.CrimsonCompass.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "EntityTypes")
@Getter
@Setter
@NoArgsConstructor
public class EntityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entityTypeId;

    @Column(name = "entity_type_name", nullable = false, unique = true)
    private String entityTypeName;

    @OneToMany(mappedBy = "entityType")
    @JsonIgnore
    private Set<Review> reviews;
}
