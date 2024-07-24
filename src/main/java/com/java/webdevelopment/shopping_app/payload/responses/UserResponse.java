package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private List<String> roleNames;

    public UserResponse() {
        roleNames = new ArrayList<>();
    }
}
