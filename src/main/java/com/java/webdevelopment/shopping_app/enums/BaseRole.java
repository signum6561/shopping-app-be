package com.java.webdevelopment.shopping_app.enums;

import java.util.Set;

import lombok.Getter;

public enum BaseRole {
    ADMIN,
    USER(
        "CREATE_OWN_ORDER",
        "DELTE_OWN_ORDER"
    );

    @Getter
    private Set<String> permissions;

    private BaseRole() {  }

    private BaseRole(String ...permissions) {
        this.permissions = Set.of(permissions);
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
