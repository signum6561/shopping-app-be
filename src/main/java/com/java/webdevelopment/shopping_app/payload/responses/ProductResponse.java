package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    
    private String id;
    private String name;
    private long price;
    private int amount;
    private boolean inStock;
    private String description;
    private String categoryName;
    private Set<String> imageLinks;
}
