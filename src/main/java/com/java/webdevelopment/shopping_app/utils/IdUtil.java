package com.java.webdevelopment.shopping_app.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.java.webdevelopment.shopping_app.constants.Contants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IdUtil {

    public String generate(int size, char[] alphabet) {
        String id = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, alphabet, size);
        return id;
    }

    public String generate(int size) {
        return generate(size, NanoIdUtils.DEFAULT_ALPHABET);
    }

    public String generate() {
        return generate(Contants.DEFAULT_ID_LENGTH);
    }

    public String generateCompact() {
        return generate(Contants.COMPACT_ID_LENGTH);
    }

}
