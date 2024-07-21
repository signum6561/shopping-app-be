package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.List;

import com.java.webdevelopment.shopping_app.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String id;
    private String username;
    private String email;
    private List<Role> roles;
}
