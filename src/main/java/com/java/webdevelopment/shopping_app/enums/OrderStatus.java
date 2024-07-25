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
        for (OrderStatus status : OrderStatus.values()) {
            if(status.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static OrderStatus convert(String code) {
        switch (code) {
            case "C":
                return OrderStatus.Canceled;
            case "O":
                return OrderStatus.OnHold;
            case "S":
                return OrderStatus.Success;
        }
        return null;
    }
}
