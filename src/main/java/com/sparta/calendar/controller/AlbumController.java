package com.sparta.calendar.controller;

import com.sparta.calendar.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
