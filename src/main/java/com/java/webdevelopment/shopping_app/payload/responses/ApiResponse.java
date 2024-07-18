package com.java.webdevelopment.shopping_app.payload.responses;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    
    private boolean success;
	private String message;

    @JsonIgnore
	private HttpStatus http;
}
