package com.app.sportshubportal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SportsHubPortalApplicationTests {
    private final String addUserApi = "/users";

    private final String administrator = "{\n" +
            "   \"username\": \"administrator12\",\n" +
            "   \"password\": \"oMoa4VvqnLxW\",\n" +
            "   \"email\": \"admin@gmail.com\"\n" +
            "   \"role\": \"ADMIN\"\n" +
            "}";

//    todo:
    @Test
    void contextLoads() {

    }

}
