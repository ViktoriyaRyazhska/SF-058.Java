package com.app.sportshubportal.services;

import com.app.sportshubportal.entities.Article;
import com.app.sportshubportal.exception.ArticleNotFoundException;
import com.app.sportshubportal.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class ArticleService {

    ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article addArticle(Article article) {
        return this.articleRepository.save(article);
    }


    public Article updateArticle(Article article) {
       return this.articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public List<Article> getAllArticles() {
        return this.articleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article with id: " + id + " was not found."));
    }

    public void removeArticle(Long id) {
         this.articleRepository.deleteById(id);
    }

}



