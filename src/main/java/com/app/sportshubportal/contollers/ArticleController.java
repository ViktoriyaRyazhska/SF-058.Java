package com.app.sportshubportal.contollers;

import com.app.sportshubportal.dto.ArticleDTO;
import com.app.sportshubportal.entities.Article;
import com.app.sportshubportal.services.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
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
    public List<ArticleDTO> list() {
            List<Article> articles = articleService.getAllArticles();
            return articles.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }


    @PostMapping
    public ArticleDTO create(@RequestBody ArticleDTO articleDto) throws ParseException {
        Article article = convertToEntity(articleDto);
        Article articleCreated = articleService.addArticle(article);
        return convertToDto(articleCreated);

    }

    @GetMapping( "/{id}")
    public ArticleDTO get(@PathVariable Long id) {
        return convertToDto(articleService.getArticleById(id));
    }

    @PatchMapping(value = "/{id}")
    public Article update(@RequestBody ArticleDTO articleDto) throws ParseException {
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

    private ArticleDTO convertToDto(Article article) {
        ArticleDTO articleDto = modelMapper.map(article, ArticleDTO.class);
        articleDto.setId(article.getId());
        articleDto.setArticleHeadline(article.getArticleHeadline());
        articleDto.setContent(article.getContent());
        articleDto.setCaption(article.getCaption());
        articleDto.setLocation(article.getLocation());
        articleDto.setSubcategory(article.getSubcategory());
        articleDto.setPicture(article.getPicture());
//        articleDto.setTags(article.getTags());
        return articleDto;
    }

    private Article convertToEntity(ArticleDTO articleDto) throws ParseException {
        Article article = modelMapper.map(articleDto, Article.class);
        article.setArticleHeadline(articleDto.getArticleHeadline());
        article.setCaption(articleDto.getCaption());
//        article.setTags(articleDto.getTags());
        article.setPicture(articleDto.getPicture());
        article.setId(articleDto.getId());
        article.setLocation(articleDto.getLocation());
        article.setContent(articleDto.getContent());
        article.setSubcategory(articleDto.getSubcategory());
        return article;
    }
}
