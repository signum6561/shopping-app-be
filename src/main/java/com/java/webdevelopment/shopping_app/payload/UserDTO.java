package com.java.webdevelopment.shopping_app.payload;

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

    private String id;

    @NotEmpty
    private String username;

    @Email
    private String email;

    @Size(min = 8)
    @NotEmpty
    private String password;
}
