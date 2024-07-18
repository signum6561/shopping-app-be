package com.java.webdevelopment.shopping_app.payload;

import java.util.List;

import com.java.webdevelopment.shopping_app.entities.Category;
import com.java.webdevelopment.shopping_app.entities.ProductImage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    
    private String id;

    @NotEmpty
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private Integer amount;

    @NotNull
    private Category category;
    
    private String description;
    
    private List<ProductImage> images;
}
