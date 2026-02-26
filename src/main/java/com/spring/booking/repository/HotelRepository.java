package com.spring.booking.repository;

import com.spring.booking.entity.Hotels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface HotelRepository extends JpaRepository<Hotels, Long> {

    @Query("""
            SELECT DISTINCT h FROM Hotels h
            LEFT JOIN h.amenities a
            LEFT JOIN h.city c
            WHERE
                (:isActive IS NULL OR h.isActive = :isActive)
            
            AND (:cityId IS NULL OR h.cityId = :cityId)
            
            AND (:ownerId IS NULL OR h.ownerId = :ownerId)
            
            AND (:minRating IS NULL OR h.rating >= :minRating)    
            AND (:amenityId IS NULL OR a.id = :amenityId)     
                               
            AND (
                :searchText IS NULL
                OR :searchText = ''
                OR LOWER(h.name) LIKE LOWER(CONCAT('%', :searchText, '%'))
                OR LOWER(h.description) LIKE LOWER(CONCAT('%', :searchText, '%'))
                OR LOWER(h.address) LIKE LOWER(CONCAT('%', :searchText, '%'))
                OR LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%'))
            )
            """)
    Page<Hotels> searchHotels(
            @Param("searchText") String searchText,
            @Param("cityId") Long cityId,
            @Param("ownerId") Long ownerId,
            @Param("minRating") BigDecimal minRating,
            @Param("amenityId") Long amenityId,
            @Param("isActive") Boolean isActive,
            Pageable pageable
    );

}