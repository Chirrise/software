package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "download")
public class Download {

    @Id
    @Column(name = "download_id", nullable = false, length = 50)
    private String downloadId;  // 下载资源编号

    @Column(nullable = false, length = 50)
    private String name;  // 资源名称

    @Column(length = 5000)
    private String description;  // 简述

    @Column(nullable = false, length = 500)
    private String url;  // 下载链接

    @Column(nullable = false, length = 50)
    private String category;  // 分类

    @Column(nullable = false, length = 50)
    private String version;  // 版本号

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;  // 发布日期

    // Getters and Setters
    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Download{" +
                "downloadId='" + downloadId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", version='" + version + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
