package com.java.webdevelopment.shopping_app.payload.requests;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {

    @NotBlank
    private String userId;

    @Pattern(regexp = "c|s|o", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String status;

    @Size(min = 1)
    private Set<ItemRequest> items;

    public OrderRequest() {
        items = new HashSet<>();
    }
}
