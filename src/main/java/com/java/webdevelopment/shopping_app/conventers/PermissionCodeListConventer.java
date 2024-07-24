package com.java.webdevelopment.shopping_app.conventers;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;

import com.java.webdevelopment.shopping_app.entities.Permission;

public class PermissionCodeListConventer extends AbstractConverter<Set<Permission>, Set<String>> {

    @Override
    protected Set<String> convert(Set<Permission> permissions) {
        return permissions.stream()
                .map(p -> p.getCode())
                .collect(Collectors.toSet());
    }

}
