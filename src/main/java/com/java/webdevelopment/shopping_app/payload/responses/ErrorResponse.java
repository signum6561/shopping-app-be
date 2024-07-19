package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus status;
    private String type;
    private Map<String, String> errors;

    public ErrorResponse() {
        errors = new HashMap<>();
    }

    public void addError(String field, String message) {
        errors.put(field, message);
    }

    public void addDefaultError(String message) {
        errors.put("defaultMessage", message);
    }
}
