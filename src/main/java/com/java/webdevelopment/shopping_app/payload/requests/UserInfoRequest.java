package com.java.webdevelopment.shopping_app.payload.requests;

import com.java.webdevelopment.shopping_app.constants.Contants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {
    
    @NotBlank
    private String username;

    @Email(message = Contants.INVALID_EMAIL_FORMAT)
    private String email;

    @Size(
        min = Contants.MIN_PASSWORD_LENGTH, 
        message = Contants.INVALID_PASSWORD_LENGTH)
    private String password;

}
