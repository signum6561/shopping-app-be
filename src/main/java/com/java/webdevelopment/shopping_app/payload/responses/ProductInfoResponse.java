package com.java.webdevelopment.shopping_app.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoResponse {
    private String id;
    private String name;
    private Long price;
    private Integer amount;
    private boolean inStock;
    private String categoryName;
    private String defaultImage;
}
