package com.app.sportshubportal.controller;

import com.app.sportshubportal.dto.ArticleDto;
import com.app.sportshubportal.entity.Article;
import com.app.sportshubportal.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    ArticleService articleService;
     ModelMapper modelMapper;

    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ArticleDto> list() {
            List<Article> articles = articleService.getAllArticles();
            return articles.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }


    @PostMapping
    public ArticleDto create(@RequestBody ArticleDto articleDto) throws ParseException {
        Article article = convertToEntity(articleDto);
        Article articleCreated = articleService.addArticle(article);
        return convertToDto(articleCreated);

    }

    @GetMapping( "/{id}")
    public ArticleDto get(@PathVariable Long id) {
        return convertToDto(articleService.getArticleById(id));
    }

    @PatchMapping(value = "/{id}")
    public Article update(@RequestBody ArticleDto articleDto) throws ParseException {
//        if(!Objects.equals(id, articleDto.getId())){
//            throw new IllegalArgumentException("IDs don't match");
//        }
        Article article = convertToEntity(articleDto);
        return articleService.updateArticle(article);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        articleService.removeArticle(id);
        return ResponseEntity.noContent().build();

    }

    private ArticleDto convertToDto(Article article) {
        ArticleDto articleDto = modelMapper.map(article, ArticleDto.class);
        articleDto.setId(article.getId());
        articleDto.setArticleHeadline(article.getArticleHeadline());
        articleDto.setContent(article.getContent());
        articleDto.setCaption(article.getCaption());
        articleDto.setLocation(article.getLocation());
        articleDto.setSubcategory(article.getSubcategory());
        articleDto.setPicture(article.getPicture());
        articleDto.setTags(article.getTags());
        return articleDto;
    }

    private Article convertToEntity(ArticleDto articleDto) throws ParseException {
        Article article = modelMapper.map(articleDto, Article.class);
        article.setArticleHeadline(articleDto.getArticleHeadline());
        article.setCaption(articleDto.getCaption());
        article.setTags(articleDto.getTags());
        article.setPicture(articleDto.getPicture());
        article.setId(articleDto.getId());
        article.setLocation(articleDto.getLocation());
        article.setContent(articleDto.getContent());
        article.setSubcategory(articleDto.getSubcategory());
        return article;
    }
}
