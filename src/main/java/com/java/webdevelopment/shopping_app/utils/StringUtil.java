package com.java.webdevelopment.shopping_app.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {
    public boolean isNullOrEmpty(String target) {
        return (target == null || target.isBlank());
    }
}