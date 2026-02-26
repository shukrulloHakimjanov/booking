package com.spring.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotels")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hotels extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "owner_id")
    Long ownerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    User owner;

    @Column(name = "name")
    String name;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "city_id", insertable = false, updatable = false)
    Long cityId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", nullable = false)
    City city;

    @Column(name = "address", columnDefinition = "text")
    String address;

    @Column(name = "rating", precision = 2, scale = 1, nullable = false)
    BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "check_in_time", nullable = false)
    LocalTime checkInTime;

    @Column(name = "check_out_time", nullable = false)
    LocalTime checkOutTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hotel_attachment",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id"))
    Set<Attachments> attachments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenities_id"))
    Set<Amenities> amenities = new HashSet<>();
}
