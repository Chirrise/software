package com.example.demo.repository;

import com.example.demo.entity.Download;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, Long> {
}
