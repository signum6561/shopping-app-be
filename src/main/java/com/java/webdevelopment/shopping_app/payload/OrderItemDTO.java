package com.java.webdevelopment.shopping_app.payload;

import com.java.webdevelopment.shopping_app.constants.Contants;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "productId")
public class OrderItemDTO {

    @NotBlank
    private String productId;

    @Min(value = 1, message = Contants.INVALID_QUANTITY_PRODUCT)
    private Integer quantity;

    @Min(value = 1, message = Contants.INVALID_PRICE_PRODUCT)
    private Long price;

    private String categoryName;
}
