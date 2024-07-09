package com.java.webdevelopment.shopping_app.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IdUtil {
    public String generate() {
        String id = NanoIdUtils.randomNanoId();
        return id;
    }

    public String generate(int size, char[] alphabet) {
        String id = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, alphabet, size);
        return id;
    }

    public String generate(int size) {
        return generate(size, NanoIdUtils.DEFAULT_ALPHABET);
    }
}
