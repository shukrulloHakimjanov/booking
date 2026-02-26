package com.spring.booking.repository;

import com.spring.booking.entity.RoomTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomTypeRepository extends JpaRepository<RoomTypes, Long> {

    @Query("SELECT DISTINCT rt FROM RoomTypes rt " +
            "LEFT JOIN FETCH rt.hotel h " +
            "WHERE rt.isActive = true AND " +
            "(LOWER(rt.name) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(rt.description) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(rt.bedConfiguration) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(h.name) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<RoomTypes> findAllWithSearch(@Param("searchText") String searchText, Pageable pageable);

    @Query("SELECT rt FROM RoomTypes rt WHERE rt.isActive = true AND rt.hotelId = :hotelId")
    Page<RoomTypes> findByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);

    @Query("SELECT rt FROM RoomTypes rt WHERE rt.isActive = true AND rt.maxGuests >= :minGuests")
    Page<RoomTypes> findByMinGuests(@Param("minGuests") Integer minGuests, Pageable pageable);


    @Query("SELECT rt FROM RoomTypes rt " +
            "WHERE rt.isActive = true AND rt.hotelId = :hotelId AND rt.maxGuests >= :minGuests")
    Page<RoomTypes> findByHotelIdAndMinGuests(@Param("hotelId") Long hotelId,
                                              @Param("minGuests") Integer minGuests,
                                              Pageable pageable);
}