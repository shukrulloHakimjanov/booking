package com.spring.booking.entity;

import com.spring.booking.constant.enums.AccommodationType;
import com.spring.booking.constant.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rooms extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "room_type_id", insertable = false, updatable = false)
    Long roomTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    RoomTypes roomType;

    @Column(name = "room_number")
    String roomNumber;

    @Column(name = "floor")
    Integer floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "accommodation_type")
    AccommodationType accommodationType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;

}
