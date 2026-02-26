package com.spring.booking.service.impl;

import com.spring.booking.configuration.minio.MinioService;
import com.spring.booking.dto.response.AttachmentDto;
import com.spring.booking.entity.Attachments;
import com.spring.booking.mapper.AttachmentMapper;
import com.spring.booking.repository.AttachmentRepository;
import com.spring.booking.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachmentServiceImpl implements AttachmentService {

    private final MinioService minioService;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    @Value("${minio.bucket}")
    private String bucketName;

    @Override
    @Transactional
    public AttachmentDto upload(MultipartFile file) {

        String key = minioService.upload(file);

        Attachments attachment = Attachments.builder()
                .key(key)
                .originalName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .bucketName(bucketName)
                .link("/api/attachments/" + key)
                .build();

        Attachments saved = attachmentRepository.save(attachment);

        return attachmentMapper.toDto(saved);
    }

    @Override
    public InputStream download(String key) {
        return minioService.download(key);
    }

    @Override
    @Transactional
    public void delete(String key) {

        Attachments attachment = attachmentRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));

        minioService.delete(key);
        attachmentRepository.delete(attachment);
    }
}
