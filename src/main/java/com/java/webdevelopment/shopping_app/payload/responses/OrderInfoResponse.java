package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderInfoResponse {
    
    private String id;

    @NotBlank
    private String status;

    @NotNull
    private Date billedDate;
}
