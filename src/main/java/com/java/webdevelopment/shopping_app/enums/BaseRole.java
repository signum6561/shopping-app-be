package com.java.webdevelopment.shopping_app.enums;

import lombok.Getter;

public enum BaseRole {
    ADMIN,
    USER;

    @Getter
    private String[] permissions;

}
