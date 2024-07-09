package com.java.webdevelopment.shopping_app.utils;

import java.util.Collection;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtil {
    public boolean isNullOrEmpty(Collection<?> target) {
        return (target == null || target.isEmpty());
    }
}