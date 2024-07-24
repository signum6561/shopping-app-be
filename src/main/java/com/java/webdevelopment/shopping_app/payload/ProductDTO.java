package com.java.webdevelopment.shopping_app.payload;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    
    private String id;

    @NotBlank
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private Integer amount;

    @NotBlank
    private String description;
    
    @NotBlank
    private String categoryId;

    private String categoryName;
    
    private Set<String> imageLinks;
}
