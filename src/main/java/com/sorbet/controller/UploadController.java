package com.sorbet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.*;
@RestController
public class UploadController {

    @PostMapping("/upload-image")
    public Map<String, String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        // ✅ 원본 확장자만 추출
        String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));

        // ✅ 안전한 파일명으로 변환 (UUID + 확장자만)
        String fileName = UUID.randomUUID() + extension;

        Path filePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Map<String, String> result = new HashMap<>();
        result.put("url", "/uploads/" + fileName); // 서버에서 접근 가능한 URL 반환
        return result;
    }

}