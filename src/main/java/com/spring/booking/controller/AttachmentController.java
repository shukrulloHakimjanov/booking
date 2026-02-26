package com.spring.booking.controller;

import com.spring.booking.dto.response.AttachmentDto;
import com.spring.booking.dto.response.MessageResponse;
import com.spring.booking.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Attachments", description = "APIs for uploading, downloading, and deleting files")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Operation(summary = "Upload file", description = "Uploads a file and returns attachment metadata")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentDto> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachmentService.upload(file));
    }

    @Operation(summary = "Download file", description = "Downloads a file using its storage key")
    @GetMapping("/{key}")
    public ResponseEntity<InputStreamResource> download(@PathVariable String key) {
        InputStream stream = attachmentService.download(key);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }

    @Operation(summary = "Delete file", description = "Deletes a file by its storage key")
    @DeleteMapping("/{key}")
    public ResponseEntity<MessageResponse> delete(@PathVariable String key) {
        attachmentService.delete(key);
        return ResponseEntity.ok(new MessageResponse("File has been deleted"));
    }
}
