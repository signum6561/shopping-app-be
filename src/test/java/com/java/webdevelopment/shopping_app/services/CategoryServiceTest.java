package com.java.webdevelopment.shopping_app.services;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    Faker faker = new Faker();

    @Test
    void testGetPaginateCategory() {
        
    }

    @Test
    void testCreateCategory() {
        
    }

    @Test
    void testDeleteCategory() {
>>>>>>> a76dcbc7950cf60a43cd638ea9b1bf0a19459976:src/test/java/com/java/webdevelopment/shopping_app/services/CategoryServiceTest.java

    Faker faker;

    UserPrincipal userPrincipal;

    @BeforeEach
    void prepare() {
        faker = new Faker();
        Product product = Product.builder()
        .id("MlSpZnV0nhyfrW-_OWj5w")
        .name("smartphone")
        .price(90000l)
        .category(null)
        .description("smartphone")
        .images(null).build();
        productRepository.save(product);   
    }

    @Test
<<<<<<< HEAD:src/test/java/com/java/webdevelopment/shopping_app/services/ProductServiceTest.java
    void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("smartphone");
        productDTO.setPrice(900000l);
        productDTO.setAmount(100);
        productDTO.setCategory(new Category());
        productDTO.setDescription("sanpham");
        productDTO.setImages(null);
        ProductDTO res = productService.createProduct(productDTO);
        System.out.println(res);
    }
=======
    void testGetAllCategory() {

    }

    @Test
    void testGetCategory() {

    }

    @Test
    void testUpdateCategory() {

>>>>>>> a76dcbc7950cf60a43cd638ea9b1bf0a19459976:src/test/java/com/java/webdevelopment/shopping_app/services/CategoryServiceTest.java
    }

}
