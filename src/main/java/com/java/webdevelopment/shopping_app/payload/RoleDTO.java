package com.java.webdevelopment.shopping_app.payload;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDTO {

    private String id;

    @NotBlank
    private String name;
    
    @NotBlank
    private String code;

    @Size(min = 1)
    @NotNull
    private Set<String> permissionCodes;

    public RoleDTO() {
        permissionCodes = new HashSet<>();
    }
}
