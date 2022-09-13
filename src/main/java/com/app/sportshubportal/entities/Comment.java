package com.app.sportshubportal.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id @GeneratedValue
    private Long id;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String text;
    @ManyToOne
    private Article article;
    @ManyToOne
    private User createdBy;

    public Comment(String text, Article article, User createdBy) {
        this.text = text;
        this.article = article;
        this.createdBy = createdBy;
    }

//    @Column(name = "test_field")
//    private String testField;

}
