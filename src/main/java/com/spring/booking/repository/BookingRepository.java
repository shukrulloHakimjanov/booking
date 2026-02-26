package com.spring.booking.repository;

import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.entity.Bookings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Bookings, UUID> {

    @Query("SELECT b FROM Bookings b WHERE b.isActive = true AND " + "(LOWER(b.user.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) " + "OR LOWER(b.user.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) " + "OR LOWER(b.hotel.name) LIKE LOWER(CONCAT('%', :searchText, '%')) " + "OR LOWER(b.room.roomNumber) LIKE LOWER(CONCAT('%', :searchText, '%')) " + "OR CAST(b.status AS string) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Bookings> findAllWithSearch(@Param("searchText") String searchText, Pageable pageable);

    @Query("SELECT b FROM Bookings b WHERE b.isActive = true AND b.userId = :userId")
    Page<Bookings> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT b FROM Bookings b WHERE b.isActive = true AND b.hotelId = :hotelId")
    Page<Bookings> findByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);

    @Query("SELECT b FROM Bookings b WHERE b.isActive = true AND b.status = :status")
    Page<Bookings> findByStatus(@Param("status") BookingStatus status, Pageable pageable);

    @Query("SELECT b FROM Bookings b WHERE b.isActive=true AND b.status=:status and b.expireDate=:date")
    List<Bookings> findByStatusAndExpireDateBefore(BookingStatus status, LocalDate date);
}