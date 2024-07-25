package com.java.webdevelopment.shopping_app.payload.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {

    @NotBlank
    private String productId;

    @Min(1)
    private Integer quantity;
}
