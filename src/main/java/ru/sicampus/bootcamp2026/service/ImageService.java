package ru.sicampus.bootcamp2026.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public ImageService() {
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Cant create downloads package", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/api/images/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Cant save file" + file.getOriginalFilename(), ex);
        }
    }

    public byte[] loadFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.readAllBytes(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("File not found" + fileName, ex);
        }
    }
}