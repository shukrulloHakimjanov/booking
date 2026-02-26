package com.spring.booking.entity;

import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.constant.enums.Currency;
import com.spring.booking.dto.response.GuestInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "booking")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bookings extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "user_id", insertable = false, updatable = false)
    Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "hotel_id", insertable = false, updatable = false)
    Long hotelId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", nullable = false)
    Hotels hotel;

    @Column(name = "room_id", insertable = false, updatable = false)
    Long roomId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    Rooms room;

    @Column(name = "check_in_date", nullable = false)
    LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    LocalDate checkOutDate;

    @Column(name = "num_guests", nullable = false)
    Integer numGuests;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "currency", length = 10)
    @Enumerated(EnumType.STRING)
    Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 40, nullable = false)
    BookingStatus status;

    @Column(name = "special_requests", columnDefinition = "TEXT")
    String specialRequests;

    @Column(name = "payment_id")
    String paymentId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "guests", columnDefinition = "jsonb",nullable = false)
    List<GuestInfo> guests;

    @Column(name = "expire_date")
    LocalDate expireDate;
}
