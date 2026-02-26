package com.spring.booking.repository;

import com.spring.booking.constant.enums.Role;
import com.spring.booking.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.isActive = true AND " +
            "(LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<User> findAllWithSearch(@Param("searchText") String searchText, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.isActive = true AND u.role = :role")
    Page<User> findByRole(@Param("role") Role role, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    boolean existsByEmail(@Param("email") String email);

    Optional<User> findByEmail(String email);
}