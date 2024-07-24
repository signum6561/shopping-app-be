package com.java.webdevelopment.shopping_app.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private String id;

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String code;
}
