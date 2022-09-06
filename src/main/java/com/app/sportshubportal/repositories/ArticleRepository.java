package com.app.sportshubportal.repositories;

import com.app.sportshubportal.entities.Article;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    boolean existsById(@NonNull Long id);
}
