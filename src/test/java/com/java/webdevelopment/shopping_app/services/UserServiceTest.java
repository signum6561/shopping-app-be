package com.java.webdevelopment.shopping_app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.payload.UserDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserProfileResponse;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    Faker faker;

    UserPrincipal userPrincipal;

    @BeforeEach
    void prepare() {
        faker = new Faker();
        User user = User.builder()
            .id("MlSpZnV0nhyfrW-_OWj5w")
            .email("example777@gmail.com")
            .username("cuong69")
            .build();
        userPrincipal = UserPrincipal.create(user);
    }

    @Test
    void testCreateUser() {
        UserDTO request = new UserDTO();
        request.setEmail("exmaple777@gmail.com");
        request.setUsername("cuong69");
        request.setPassword(faker.internet().password());
        UserProfileResponse res = userService.createUser(request);
        System.out.println(res);
    }

    @Test
    void testGetPaginateUser() {
        PageResponse<UserProfileResponse> users = userService.getPaginateUser(0, 5);
        System.out.println(users);
    }

    @Test
    void testGetUser() {
        String id = "MlSpZnV0nhyfrW-_OWj5w";
        UserProfileResponse res = userService.getUser(id);
        System.out.println(res); 
    }

    @Test
    void testUpdateUserByUsername() {
        String id = "MlSpZnV0nhyfrW-_OWj5w";
        UserDTO userDto = new UserDTO();
        userDto.setEmail("thoai29");
        userDto.setPassword("exagfd7@gmail.com");
        userDto.setUsername("thoai29");
        UserProfileResponse res = userService.updateUser(id, userDto, userPrincipal);
        System.out.println(res);
    }

    @Test
    void testDeleteUserByUsername() {
        String id = "MlSpZnV0nhyfrW-_OWj5w";
        ApiResponse res = userService.deleteUser(id);
        System.out.println(res);
    }

    @Test
    void testGetCurrentUser() {
        UserProfileResponse res = userService.getCurrentUser(userPrincipal);
        System.out.println(res);
    }

    @Test
    void testIsEmailExists() {
        boolean res = userService.isEmailExists("exmaple777@gmail.com");
        assertEquals(true, res);
    }

    @Test
    void testIsUsernameExists() {
        boolean res = userService.isUsernameExists("exmaple777@gmail.com");
        assertEquals(true, res);
    }
}
