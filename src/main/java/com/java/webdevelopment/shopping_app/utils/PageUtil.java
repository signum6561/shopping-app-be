package com.java.webdevelopment.shopping_app.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtil {
    public Pageable request(Integer page, Integer pageSize) {
        ValidateUtil.validatePaginateParams(page, pageSize);
        if(page == 1) {
            page = 0;   
        }
        return PageRequest.of(page, pageSize);
    }
}
