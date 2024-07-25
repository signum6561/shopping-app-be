package com.java.webdevelopment.shopping_app.payload.requests;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserOrderRequest {
    
    @NotNull
    @Size(min = 1)
    private Set<ItemRequest> items;

    public UserOrderRequest() {
        items = new HashSet<>();
    }
}
