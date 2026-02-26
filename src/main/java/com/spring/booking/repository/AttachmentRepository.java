package com.spring.booking.repository;

import com.spring.booking.entity.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachments, Long> {
    @Query("SELECT a FROM Attachments a WHERE a.key = :key")
    Optional<Attachments> findByKey(@Param("key") String key);

}
