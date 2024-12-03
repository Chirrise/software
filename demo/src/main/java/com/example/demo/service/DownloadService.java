package com.example.demo.service;

import com.example.demo.entity.Download;
import com.example.demo.repository.DownloadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DownloadService {
    private final DownloadRepository downloadRepository;

    public DownloadService(DownloadRepository downloadRepository) {
        this.downloadRepository = downloadRepository;
    }

    public List<Download> getAllDownloads() {
        return downloadRepository.findAll();
    }
}
