package com.example.demo.controller;

import com.example.demo.entity.Download;
import com.example.demo.service.DownloadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/download")
public class DownloadController {
    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping
    public List<Download> getAllDownloads() {
        return downloadService.getAllDownloads();
    }
}
