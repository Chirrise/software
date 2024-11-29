package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{category}")
    public List<Article> getArticlesByCategory(@PathVariable String category) {
        return articleService.getArticlesByCategory(category);
    }
}
