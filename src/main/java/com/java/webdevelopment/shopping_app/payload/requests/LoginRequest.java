package com.java.webdevelopment.shopping_app.payload.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

    @NotEmpty
    private String username;

    @NotEmpty
	private String password;
}
