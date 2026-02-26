package com.spring.booking.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room_types")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class RoomTypes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "hotel_id", insertable = false, updatable = false)
    Long hotelId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", nullable = false)
    Hotels hotel;

    @Column(name = "name", nullable = false, length = 100)
    String name;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "max_guests", nullable = false)
    Integer maxGuests;

    @Column(name = "bed_configuration", columnDefinition = "TEXT")
    String bedConfiguration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_type_amenties",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenities_id")
    )
    Set<Amenities> amenities;
}
