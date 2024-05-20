package com.sparta.calendar.controller;

import com.sparta.calendar.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String uploadFolder = "D:\\DD\\stude\\Java\\F_rank";
        String filePath = albumService.uploadFile(file, uploadFolder);
        if (filePath != null) {
            return "File uploaded successfully. Path: " + filePath;
        } else {
            return "Failed to upload file.";
        }
    }

}
