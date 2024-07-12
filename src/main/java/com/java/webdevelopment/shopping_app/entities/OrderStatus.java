package com.java.webdevelopment.shopping_app.entities;

import lombok.Getter;

public enum OrderStatus {

    Canceled("C"),
    OnHold("O"),
    Success("S");

    @Getter
    private String code;

    private OrderStatus(String code) {
        this.code = code;
    }
}
