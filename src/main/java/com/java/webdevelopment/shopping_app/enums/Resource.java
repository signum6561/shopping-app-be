package com.java.webdevelopment.shopping_app.enums;

import java.util.Set;

import lombok.Getter;

public enum Resource {
    CATEGORY(
        "UPDATE",
        "CREATE",
        "DELETE"
    ),
    ROLE(
        "READ_ALL",
        "UPDATE",
        "CREATE",
        "DELETE"
    ),
    PRODUCT(
        "UPDATE",
        "CREATE",
        "DELETE"
    ),
    USER(
        "READ_ALL",
        "UPDATE",
        "CREATE",
        "DELETE"
    ),
    ORDER(
        "READ_ALL",
        "UPDATE",
        "CREATE",
        "DELETE"
    );

    @Getter
    private Set<String> permissions;

    private Resource() { }

    private Resource(String ...permissions) {
        this.permissions = Set.of(permissions);
    }

    public static String convertToCode(Resource resource) {
        return "_" + resource.toString();
    }
}
