package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.List;

import com.java.webdevelopment.shopping_app.entities.Order;
import com.java.webdevelopment.shopping_app.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String email;
    private String username;
    private List<Role> roles;
    private List<Order> orders;

}
