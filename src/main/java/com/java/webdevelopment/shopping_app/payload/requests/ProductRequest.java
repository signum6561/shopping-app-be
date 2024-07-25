package com.java.webdevelopment.shopping_app.payload.requests;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank
    private String name;

    @Min(1)
    @NotNull
    private Long price;

    @Min(1)
    @NotNull
    private Integer amount;

    @NotBlank
    private String description;
    
    @NotBlank
    private String categoryId;

}
