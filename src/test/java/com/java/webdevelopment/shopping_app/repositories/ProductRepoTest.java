package com.java.webdevelopment.shopping_app.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.java.webdevelopment.shopping_app.entities.Category;
import com.java.webdevelopment.shopping_app.entities.Product;
import com.java.webdevelopment.shopping_app.utils.IdUtil;

@SpringBootTest
public class ProductRepoTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    Faker faker = new Faker();

    @Test
    void generateFakeData() {
        int n = 20;
        List<Category> listCategories = categoryRepository.findAll();
        productRepository.deleteAll();
        while (n > 0) {
            n--;
            Product product = Product.builder()
                .id(IdUtil.generate())
                .name(faker.commerce().productName())
                .price(faker.random().nextLong(50000000))
                .amount(faker.random().nextInt(100))
                .description(faker.lorem().paragraph())
                .category(faker.options().nextElement(listCategories))
                .build();
            productRepository.save(product);
        }
    }

    @Test
    void createNewProduct() {
        Optional<Category> ca = categoryRepository.findById("AxY7hokHQK--XR31MznNP"); 
        Product product = Product.builder()
            .id(IdUtil.generate())
            .name(faker.commerce().productName())
            .price(faker.random().nextLong())
            .amount(faker.random().nextInt(0, 100))
            .description(faker.lorem().paragraph())
            .category(ca.get())
            .build();
        productRepository.save(product);
    }

    @Test
    void getProduct() {
        String id = "zIR3O2GCBg5-Weu_HUIe0";
        Optional<Product> product = productRepository.findById(id);
        System.out.println(product.get());
    }

    @Test
    void deleteProduct() {
        productRepository.deleteById("zIR3O2GCBg5-Weu_HUIe0");
    }
}
