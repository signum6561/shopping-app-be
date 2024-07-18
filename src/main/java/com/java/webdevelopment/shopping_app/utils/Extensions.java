package com.java.webdevelopment.shopping_app.utils;

import java.util.Collection;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Extensions {

    public boolean isNullOrEmpty(Object target) {
        if (target instanceof String) {
            return StringUtil.isNullOrEmpty((String) target);
        }

        if (target instanceof Collection) {
            return CollectionUtil.isNullOrEmpty((Collection<?>) target);
        }
        return (target == null);
    }

}
