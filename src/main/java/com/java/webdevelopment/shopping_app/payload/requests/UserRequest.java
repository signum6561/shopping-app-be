package com.java.webdevelopment.shopping_app.payload.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotEmpty
    private String username;

    @Email
    private String email;

    @Size(min = 8)
    private String password;
}
