package com.spring.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "link")
    String link;

    @Column(unique = true)
    String key;

    @Column(name = "original_name")
    String originalName;

    @Column(name = "content_type")
    String contentType;

    @Column(name = "bucket_name")
    String bucketName;
}
