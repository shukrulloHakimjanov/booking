package com.spring.booking.repository;

import com.spring.booking.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CitiesRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.isActive = true AND " +
            "(LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(c.country) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<City> findAllWithSearch(@Param("searchText") String searchText, Pageable pageable);

}