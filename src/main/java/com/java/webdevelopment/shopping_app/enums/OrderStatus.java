package com.java.webdevelopment.shopping_app.enums;

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

    public static boolean existByCode(String code) {
        String c = code.toUpperCase();
        for (OrderStatus status : OrderStatus.values()) {
            if(status.getCode().equals(c)) {
                return true;
            }
        }
        return false;
    }

    public static OrderStatus convert(String code) {
        switch (code.toLowerCase()) {
            case "c":
                return OrderStatus.Canceled;
            case "o":
                return OrderStatus.OnHold;
            case "s":
                return OrderStatus.Success;
        }
        return null;
    }
}
