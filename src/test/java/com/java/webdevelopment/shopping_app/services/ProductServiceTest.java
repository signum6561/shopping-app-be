package com.java.webdevelopment.shopping_app.services;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.java.webdevelopment.shopping_app.entities.Category;
import com.java.webdevelopment.shopping_app.entities.Product;
import com.java.webdevelopment.shopping_app.payload.ProductDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;
import com.java.webdevelopment.shopping_app.repositories.ProductRepository;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;


@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

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

    @Test
    void testDeleteProductById() {
       String id = "MlSpZnV0nhyfrW-_OWj5w";
       ApiResponse res= productService.deleteProduct(id);
       System.out.println(res);
    }

    @Test
    void testGetPaginateProduct() {
        PageResponse<ProductInfoResponse> products = productService.getPaginateProduct(0, 5);
        System.out.println(products);
    }

    @Test
    void testGetProduct() {
        String id = "MlSpZnV0nhyfrW-_OWj5w";
        ProductDTO res= productService.getProduct(id);
        System.out.println(res);
    }

    @Test
    void testUpdateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("smartphone");
        productDTO.setPrice(900000l);
        productDTO.setAmount(100);
        productDTO.setCategory(new Category());
        productDTO.setDescription("sanpham");
        productDTO.setImages(null);
        ProductDTO res= productService.updateProduct(productDTO);
        System.out.println(res);
    }

}
