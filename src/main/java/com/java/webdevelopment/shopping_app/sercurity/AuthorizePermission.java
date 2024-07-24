package com.java.webdevelopment.shopping_app.sercurity;

import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizePermission {
    public boolean permission(MethodSecurityExpressionOperations root, String permission) {
        if(root.hasRole("ADMIN")) {
            return true;
        }
        String[] codes = permission.split(":");
        return root.hasAuthority(codes[0].toUpperCase() + "_" + codes[1].toUpperCase());
    }
}
