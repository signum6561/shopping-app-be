package com.java.webdevelopment.shopping_app.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleInfoResponse {
    private String id;
    private String name;
    private String code;
}
