package com.backend.CrimsonCompass.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "Amenities")
@Getter
@Setter
@NoArgsConstructor
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer amenityId;

    @Column(nullable = false, unique = true)
    private String name;

    private String icon;

    private String color;

    @ManyToMany(mappedBy = "amenities")
    private Set<Hotel> hotels;
}
