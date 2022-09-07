package com.app.sportshubportal;

import com.app.sportshubportal.entitites.User;
import com.app.sportshubportal.roles.Role;
import com.app.sportshubportal.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SportsHubPortalApplicationTests {
    private final String addUserApi = "/users";

    private final String administrator = "{\n"
            + "     \"username\": \"administrator12\",\n"
            + "     \"password\": \"oMoa4VvqnLxW\",\n"
            + "     \"email\": \"admin@gmail.com\"\n"
            + "     \"role\": \"ADMIN\"\n"
            + "}";

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
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

        assert(test1);
        assert(test2);
        assert(test3);
    }

}
