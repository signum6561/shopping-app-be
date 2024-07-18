package com.java.webdevelopment.shopping_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.webdevelopment.shopping_app.entities.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

}
