package com.java.webdevelopment.shopping_app.conventers;

import java.util.List;
import java.util.Set;

import org.modelmapper.AbstractConverter;

import com.java.webdevelopment.shopping_app.entities.Role;

public class RoleNameListConventer extends AbstractConverter<Set<Role>, List<String>> {

    @Override
    protected List<String> convert(Set<Role> roles) {
        return roles.stream()
                .map(r -> r.getName())
                .toList();
    }

}
