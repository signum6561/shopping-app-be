package com.java.webdevelopment.shopping_app.payload;

import com.java.webdevelopment.shopping_app.constants.Contants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    private String username;

    @Email(message = Contants.INVALID_EMAIL_FORMAT)
    private String email;

    @Size(
        min = Contants.MIN_PASSWORD_LENGTH, 
        message = Contants.INVALID_PASSWORD_LENGTH)
    @NotEmpty
    private String password;
}
