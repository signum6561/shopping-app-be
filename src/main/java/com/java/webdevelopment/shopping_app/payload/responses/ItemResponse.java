package com.java.webdevelopment.shopping_app.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "productId")
public class ItemResponse {
    private String productId;
    private String productName;
    private Integer quantity;
    private Long itemTotal;
}
