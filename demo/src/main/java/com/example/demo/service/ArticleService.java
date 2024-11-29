package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getArticlesByCategory(String category) {
        return articleRepository.findByCategory(category);
    }
}
