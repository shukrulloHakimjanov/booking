package com.spring.booking.repository;

import com.spring.booking.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "LEFT JOIN r.user u " +
            "LEFT JOIN r.hotel h " +
            "WHERE r.isActive = true AND " +
            "(LOWER(r.comment) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(h.name) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Review> findAllWithSearch(@Param("searchText") String searchText, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.isActive = true AND r.hotelId = :hotelId")
    Page<Review> findByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.isActive = true AND r.userId = :userId")
    Page<Review> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.isActive = true AND r.bookingId = :bookingId")
    Page<Review> findByBookingId(@Param("bookingId") Long bookingId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.isActive = true AND r.rating >= :minRating")
    Page<Review> findByMinRating(@Param("minRating") Integer minRating, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.isActive = true AND " +
            "r.hotelId = :hotelId AND r.rating >= :minRating")
    Page<Review> findByHotelIdAndMinRating(@Param("hotelId") Long hotelId,
                                           @Param("minRating") Integer minRating,
                                           Pageable pageable);
}