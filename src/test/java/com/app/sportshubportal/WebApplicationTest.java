package com.app.sportshubportal;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.sportshubportal.entities.User;
import com.app.sportshubportal.exception.UserAlreadyExistsException;
import com.app.sportshubportal.repositories.UserRepo;
import com.app.sportshubportal.roles.Role;
import com.app.sportshubportal.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.List;


@WebAppConfiguration(value = "")
@SpringBootTest
@AutoConfigureMockMvc
public class WebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

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

        var user1 = userService.registerUser(user);

        var test1 = user1.getUsername().equals("John");
        var test2 = user1.getRole().equals(Role.ADMIN);
        var test3 = user1.isAccountNonLocked() == true;

        assertThat(test1).isTrue();
        assertThat(test2).isTrue();
        assertThat(test3).isTrue();
    }


    @Test
    void test2() throws Exception {

        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
        // Expecting status okay
    }

    @Test
    void test3() throws Exception {

        this.mockMvc.perform(get("/users/6"))
                .andExpect(status().isNotFound());
        // Expecting isNotFound here because its not created with exactly id: 6
    }

    @Test
    void test4() throws Exception {
        User user = new User();
        user.setUsername("NewUsername");
        user.setPassword("password");
        user.setEmail("new-username@gmail.com");
        user.setRole(Role.ADMIN);
        user.setProfilePicturePath("newuser.png");

        userService.registerUser(user); // Mock

        this.mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());

        userService.deleteUser(user.getId());
        // Expecting okay status because the user is already register and have id:1
    }

    @Test
    void test5() throws Exception {
        User user1 = new User();
        user1.setUsername("John");
        user1.setPassword("johnpassword");
        user1.setEmail("johnsmith@hotmail.com");
        user1.setRole(Role.USER);
        user1.setProfilePicturePath("johnpicture.png");


        userService.registerUser(user1);

        List<User> allUsers = userService.findAllUsers();

        boolean test = allUsers.size() == 1;
        System.out.println("The size of the list is: " + allUsers.size());
        assertTrue(test);

        userService.deleteUser(user1.getId());
    }

    @Test
    void test6() throws Exception {
        User user2 = new User();
        user2.setUsername("Isabell");
        user2.setPassword("isabellpassword");
        user2.setEmail("isabel@gmail.com");
        user2.setRole(Role.USER);
        user2.setProfilePicturePath("isabelpicture.png");

        userService.registerUser(user2);

        List<User> allUsers = userService.findAllUsers();

        boolean test = allUsers.size() == 1;
        System.out.println("The size of the list is: " + allUsers.size());
        assertTrue(test);

        userService.deleteUser(user2.getId());
    }

    @Test
    void test7() throws Exception {
        User john1 = new User();
        john1.setUsername("User");
        john1.setPassword("johnpassword");
        john1.setEmail("johnsmith@hotmail.com");
        john1.setRole(Role.USER);
        john1.setProfilePicturePath("johnpicture.png");

        User john2 = new User();
        john2.setUsername("User");
        john2.setPassword("johnpassword");
        john2.setEmail("johnsmith@hotmail.com");
        john2.setRole(Role.USER);
        john2.setProfilePicturePath("johnpicture.png");

        userService.registerUser(john1);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser(john2);
        });

    }

    @Test
    void test9() throws Exception {
        User user1 = new User();
        user1.setUsername("");
        user1.setPassword("tompassword");
        user1.setEmail("tom@example.com");
        user1.setRole(Role.USER);
        user1.setProfilePicturePath("tompicture.png");


        assertThrows(ConstraintViolationException.class, () -> {
            userService.registerUser(user1);
        });
    }

    @Test
    void test10() throws Exception {
        User user2 = new User();
        user2.setUsername("");
        user2.setPassword("melisapassword");
        user2.setEmail("melissa@example.com");
        user2.setRole(Role.ADMIN);
        user2.setProfilePicturePath("melissapicture.png");

        assertThrows(ConstraintViolationException.class, () -> {
            userService.registerUser(user2);
        });
    }

    @Test
    void test11() throws Exception {
        User newUser = new User();
        newUser.setUsername("New Username");
        newUser.setPassword("123456789");
        newUser.setEmail("example@gmail.com");
        newUser.setRole(Role.USER);
        newUser.setProfilePicturePath("newusername.png");

        assertNotNull(newUser.getUsername(), "User should be found");

    }

    @Test
    void test12() throws UsernameNotFoundException {
        User chad = new User();
        chad.setUsername("Chad");
        chad.setPassword("chadpassword");
        chad.setEmail("chad@hotmail.com");
        chad.setRole(Role.ADMIN);
        chad.setProfilePicturePath("chadpicture.png");


        userService.registerUser(chad);

        assertDoesNotThrow(() -> {
            userService.loadUserByUsername(chad.getUsername());
        }, "User should be found");
    }

    @Test
    void test13() throws Exception {
        User jonatham = new User();
        jonatham.setUsername("Jonatham");
        jonatham.setPassword("jonathampassword");
        jonatham.setEmail("jonatham@gmail.com");
        jonatham.setRole(Role.ADMIN);
        jonatham.setProfilePicturePath("jonatham.png");

        userService.registerUser(jonatham);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("Jonatham"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("jonatham@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicturePath").value("jonatham.png"));

    }

    @Test
    void test14() throws Exception {
        User sherlock = new User();
        sherlock.setUsername("Sherlock");
        sherlock.setPassword("sherlockpassword");
        sherlock.setEmail("sherlock-holmes@gmail.com");
        sherlock.setRole(Role.ADMIN);
        sherlock.setProfilePicturePath("sherlock.png");

        mockMvc.perform(delete("/users"))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException));

    }

    @Test
    void test15() throws Exception {
        User newUser = new User();
        newUser.setUsername("newUsername");
        newUser.setPassword("newusernamepassword");
        newUser.setEmail("new-username@hotmail.com");
        newUser.setRole(Role.USER);
        newUser.setProfilePicturePath("newusername.png");

        userService.registerUser(newUser);

        assertDoesNotThrow(() -> {
            userService.loadUserByUsername(newUser.getUsername());
        }, "User should be found");

    }

    @Test
    void test16() throws Exception {
        User user = new User();
        user.setUsername("Username");
        user.setPassword("usernamepassword");
        user.setEmail("username@gmail.com");
        user.setRole(Role.ADMIN);
        user.setProfilePicturePath("username.png");

        userService.registerUser(user);

        assertDoesNotThrow(() -> {
            userService.findAllUsers();
        }, "Should not throw any exception because the user already is registered.");

    }

    @Test
    void test17() throws Exception {
        User tom = new User();
        tom.setUsername("Tom");
        tom.setPassword("tompassword");
        tom.setEmail("tom-hard@hotmail.com");
        tom.setRole(Role.USER);
        tom.setProfilePicturePath("tompicture.png");

        List<User> users = List.of();

        assertEquals(userService.findAllUsers(), users);
    }

    @Test
    void test18() throws Exception {
        User rebecca = new User();
        rebecca.setUsername("Rebecca");
        rebecca.setPassword("rebeccapassword");
        rebecca.setEmail("rebecca@gmail.com");
        rebecca.setRole(Role.USER);
        rebecca.setProfilePicturePath("rebeccapicture.png");

        userService.registerUser(rebecca);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("Rebecca"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("rebecca@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicturePath").value("rebeccapicture.png"));

    }

    @Test
    void test19() throws Exception {
        User nugget = new User();
        nugget.setId(1L);
        nugget.setUsername("Nugget");
        nugget.setPassword("intellij");
        nugget.setEmail("nugget@hotmail.com");
        nugget.setRole(null);
        nugget.setProfilePicturePath("nuggetpicture.png");


        assertThrows(UnexpectedTypeException.class, () -> {
            userService.registerUser(nugget);
        }); // because we are setting the Role null
    }

    @Test
    void test20() throws Exception {

        mockMvc.perform(put("/users/print"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

}
