package com.java.webdevelopment.shopping_app.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.java.webdevelopment.shopping_app.entities.Category;
import com.java.webdevelopment.shopping_app.utils.IdUtil;

@SpringBootTest
public class CategoryRepoTest {

    @Autowired
    CategoryRepository categoryRepository;

    Faker faker = new Faker();

    @Test
    void generateFakeData() {
        int n = 10;
        categoryRepository.deleteAll();
        while(n > 0) {
            n--;
            Category category = Category.builder()
                .id(IdUtil.generate())
                .name(faker.commerce().material())
                .build();
            categoryRepository.save(category);
        }
    }

    @Test
    void testCreate() {
        Category category = Category.builder()
            .id(IdUtil.generate())
            .name("Dummy Category")
            .build();
        categoryRepository.save(category);
    }
}
