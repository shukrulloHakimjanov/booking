package com.spring.booking.service;

import com.spring.booking.dto.response.AttachmentDto;
import com.spring.booking.entity.Attachments;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface AttachmentService {

    AttachmentDto upload(MultipartFile file);

    InputStream download(String key);

    void delete(String key);
}
