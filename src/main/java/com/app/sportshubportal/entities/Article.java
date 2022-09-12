package com.app.sportshubportal.entities;

import com.app.sportshubportal.ArticleCategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="articles")
@NoArgsConstructor
@Getter @Setter
public class Article {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(nullable = false, updatable = false)
     Long id;

     String articleHeadline;

     @ManyToOne
     @JoinColumn(name = "author_id")

     User author;

     String caption;

     String content;

    //TODO ELement collection
//     Set<String> tags; //should be entity; ManyToMany

     @Enumerated(EnumType.STRING)
     ArticleCategoryEnum subcategory;

     String location;

     String picture;

     @OneToMany(mappedBy = "article")
     List<Comment> comments;

     public Article(String articleHeadline, User author, String caption, String content, ArticleCategoryEnum subcategory, String location, String picture, List<Comment> comments) {
          this.articleHeadline = articleHeadline;
          this.author = author;
          this.caption = caption;
          this.content = content;
          this.subcategory = subcategory;
          this.location = location;
          this.picture = picture;
          this.comments = comments;
     }
}
