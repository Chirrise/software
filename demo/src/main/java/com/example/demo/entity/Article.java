package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @Column(name = "article_id", nullable = false, length = 50)
    private String articleId;  // 文章编号

    @Column(nullable = false, length = 50)
    private String title;  // 标题

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 文章内容

    @Column(nullable = false, length = 50)
    private String category;  // 分类

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;  // 发布日期

    @Column(nullable = false, length = 50)
    private String author;  // 作者

    // Getters and Setters
    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", publishDate=" + publishDate +
                ", author='" + author + '\'' +
                '}';
    }
}
