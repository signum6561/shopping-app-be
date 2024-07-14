package com.java.webdevelopment.shopping_app.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.java.webdevelopment.shopping_app.entities.User;

@SpringBootTest
public class UserRepoTest {

    @Autowired
    UserRepository userRepository;

    Faker faker = new Faker();

    @Test
    void generateFakeData() {
        int n = 5;
        while(n > 0) {
            n--;
            User user = User.builder()
                .id(faker.idNumber().ssnValid())
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
            userRepository.save(user);
        }
    }

    @Test
    void createNewUser() throws Exception {
        User user = User.builder()
            .id("123")
            .username("dummy")
            .email("example@gmail.com")
            .password("123456")
            .build();
        userRepository.save(user);
    }

    @Test
    void getAll() throws Exception {
        List<User> users = userRepository.findAll();
        System.out.println(users);
    }

    @Test
    void getUser() throws Exception {
        Optional<User> user = userRepository.findById("123");
        System.out.println(user);
    }

    @Test
    void updateUser() throws Exception {
        Optional<User> user = userRepository.findById("123");
        user.get().setUsername("Dummy123");
    }

    @Test
    void deleteUser() throws Exception {
        userRepository.deleteById("123");
    }

}
