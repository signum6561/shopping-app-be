package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;

public interface UserAuthService {
    UserPrincipal loadUserById(String userId);
}
