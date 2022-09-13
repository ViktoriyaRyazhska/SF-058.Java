package com.app.sportshubportal;

import com.app.sportshubportal.dto.CommentDTO;
import com.app.sportshubportal.entities.Article;
import com.app.sportshubportal.entities.User;
import com.app.sportshubportal.repositories.UserRepo;
import com.app.sportshubportal.roles.Role;
import com.app.sportshubportal.services.CommentService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@WebAppConfiguration(value = "")
@SpringBootTest
@AutoConfigureMockMvc
public class CommentsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserRepo userRepo;


    @Test
    void test1() {
        User user = new User();
        user.setUsername("John");
        user.setPassword("johnpassword");
        user.setEmail("john_smith@gmail.com");
        user.setRole(Role.ADMIN);
        user.setProfilePicturePath("john.png");

        Article article = new Article();
        article.setId(22L);
        article.setArticleHeadline("Headline");
        article.setCaption("Caption");

        CommentDTO comment = new CommentDTO();
        comment.setText("Test comment");


        var comment1 = commentService.add(22L, user, comment);

    }
}