package com.java.webdevelopment.shopping_app.enums;

import lombok.Getter;

public enum BaseRole {
    ADMIN,
    USER(
        "READ_ORDER_OWNER",
        "CREATE_ORDER_OWNER",
        "DELETE_ORDER_OWNER"
    );

    @Getter
    private String[] permissions;

    private BaseRole() {  }

    private BaseRole(String ...permissions) {
        this.permissions = permissions;
    }

    public static boolean existByName(String name) {
        for (BaseRole baseRole : BaseRole.values()) {
            if(baseRole.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
