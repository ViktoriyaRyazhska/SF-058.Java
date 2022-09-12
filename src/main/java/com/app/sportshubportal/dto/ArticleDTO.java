package com.app.sportshubportal.dto;

import com.app.sportshubportal.ArticleCategoryEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    String articleHeadline;

    String caption;

    String content;

//    Set<String> tags;

    ArticleCategoryEnum subcategory;

    String location;

    String picture;
}
