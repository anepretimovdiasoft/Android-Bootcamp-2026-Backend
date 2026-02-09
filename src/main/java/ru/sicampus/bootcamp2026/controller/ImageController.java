package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sicampus.bootcamp2026.dto.ImageDtos.ImageResponse;
import ru.sicampus.bootcamp2026.service.ImageService;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("image") MultipartFile file) {
        String fileUrl = imageService.storeFile(file);
        String fullUrl = "http://10.0.2.2:8080" + fileUrl;
        return ResponseEntity.ok(new ImageResponse(fullUrl));
    }

    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String fileName) {
        return imageService.loadFile(fileName);
    }
}
