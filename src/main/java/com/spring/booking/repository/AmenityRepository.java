package com.spring.booking.repository;

import com.spring.booking.entity.Amenities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AmenityRepository extends JpaRepository<Amenities, Long> {

    @Query("SELECT a FROM Amenities a WHERE a.isActive = true AND " +
            "(LOWER(a.name) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(a.category) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Amenities> findAllWithSearch(@Param("searchText") String searchText, Pageable pageable);


}