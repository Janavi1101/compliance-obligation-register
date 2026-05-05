package com.internship.tool.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.internship.tool.entity.FileDocument;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.FileRepository;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public FileDocument uploadFile(MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is required");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds 10MB");
        }

        String type = file.getContentType();

        if (type == null ||
                !(type.equals("application/pdf") || type.startsWith("image/"))) {
            throw new RuntimeException("Only PDF or image files are allowed");
        }

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalName = file.getOriginalFilename();

        if (originalName == null || originalName.isBlank()) {
            originalName = "uploaded-file";
        }

        String safeOriginalName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String uuidFileName = UUID.randomUUID() + "_" + safeOriginalName;

        Path filePath = uploadPath.resolve(uuidFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileDocument doc = new FileDocument();
        doc.setOriginalName(originalName);
        doc.setFileName(uuidFileName);
        doc.setFileType(type);
        doc.setFilePath(filePath.toString());

        return repository.save(doc);
    }

    public FileDocument getFile(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + id));
    }
}