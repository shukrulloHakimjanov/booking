package com.spring.booking.repository;

import com.spring.booking.constant.enums.Status;
import com.spring.booking.entity.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Rooms, Long> {

    @Query("SELECT r FROM Rooms r " +
            "LEFT JOIN r.roomType rt " +
            "WHERE r.isActive = true AND " +
            "(LOWER(r.roomNumber) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(rt.name) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR CAST(r.floor AS string) LIKE CONCAT('%', :searchText, '%'))")
    Page<Rooms> findAllWithSearch(@Param("searchText") String searchText, Pageable pageable);

    @Query("SELECT r FROM Rooms r WHERE r.isActive = true AND r.roomTypeId = :roomTypeId")
    Page<Rooms> findByRoomTypeId(@Param("roomTypeId") Long roomTypeId, Pageable pageable);

    @Query("SELECT r FROM Rooms r " +
            "JOIN r.roomType rt " +
            "WHERE r.isActive = true AND rt.hotelId = :hotelId")
    Page<Rooms> findByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);

    @Query("SELECT r FROM Rooms r WHERE r.isActive = true AND r.status = :status")
    Page<Rooms> findByStatus(@Param("status") Status status, Pageable pageable);

    @Query("SELECT r FROM Rooms r WHERE r.isActive = true AND r.floor = :floor")
    Page<Rooms> findByFloor(@Param("floor") Integer floor, Pageable pageable);

    @Query("SELECT r FROM Rooms r " +
            "JOIN r.roomType rt " +
            "WHERE r.isActive = true AND rt.hotelId = :hotelId AND r.status = :status")
    Page<Rooms> findByHotelIdAndStatus(@Param("hotelId") Long hotelId,
                                       @Param("status") Status status,
                                       Pageable pageable);
}