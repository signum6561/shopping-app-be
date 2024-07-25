package com.java.webdevelopment.shopping_app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.payload.requests.UserInfoRequest;
import com.java.webdevelopment.shopping_app.payload.requests.UserRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserResponse;
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
            .id("HzLKVwWPsrLAYRWlL5YGw")
            .email("example777@gmail.com")
            .username("admin")
            .build();
        userPrincipal = new UserPrincipal(user);
    }

    @Test
    void testCreateUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("exmaple777@gmail.com");
        request.setUsername("cuong69");
        request.setPassword(faker.internet().password());
        UserResponse res = userService.createUser(request);
        System.out.println(res);
    }

    @Test
    void testGetPaginateUser() {
        PageResponse<UserResponse> users = userService.getPaginateUser(0, 5);
        System.out.println(users);
    }

    @Test
    void testGetUser() {
        String id = "VCp9vhJq3hHm0GIqazH-e";
        UserResponse res = userService.getUser(id);
        System.out.println(res); 
    }

    @Test
    void testUpdateUser() {
        String id = "VCp9vhJq3hHm0GIqazH-e";
        UserRequest request = new UserRequest();
        request.setEmail("dasaf@gmail.com");
        request.setPassword("cuont235");
        request.setUsername("thoai20");
        UserResponse res = userService.updateUser(id, request);
        System.out.println(res);
    }

    @Test
    void testUpdateUserProfile() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("dasaf@gmail.com");
        request.setPassword("cuont235");
        request.setUsername("thoai29");
        UserResponse res = userService.updateProfile(userPrincipal, request);
        System.out.println(res);
    }

    @Test
    void testDeleteUser() {
        String id = "VCp9vhJq3hHm0GIqazH-e";
        ApiResponse res = userService.deleteUser(id);
        System.out.println(res);
    }

    @Test
    void testSelfDelete() {
        ApiResponse res = userService.selfDelete(userPrincipal);
        System.out.println(res);
    }

    @Test
    void testGetCurrentUser() {
        UserResponse res = userService.getCurrentUser(userPrincipal);
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
