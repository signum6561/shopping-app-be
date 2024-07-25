package com.java.webdevelopment.shopping_app.payload.requests;

import java.util.HashSet;
import java.util.Set;

import com.java.webdevelopment.shopping_app.constants.Contants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    
    @NotBlank
    private String username;

    @Email(message = Contants.INVALID_EMAIL_FORMAT)
    @NotBlank
    private String email;

    @Size(
        min = Contants.MIN_PASSWORD_LENGTH, 
        message = Contants.INVALID_PASSWORD_LENGTH)
    @NotBlank
    private String password;

    private Set<String> roleCodes;

    public UserRequest() {
        roleCodes = new HashSet<>();
    }
    
}
