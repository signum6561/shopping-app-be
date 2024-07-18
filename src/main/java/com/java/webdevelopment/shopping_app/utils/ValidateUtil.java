package com.java.webdevelopment.shopping_app.utils;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.exceptions.InvalidRequestException;

import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidateUtil {
    public void validatePaginateParams(
        @NotNull(message = Contants.NULL_PAGE_INDEX) Integer page, 
        @NotNull(message = Contants.NULL_PAGE_SIZE) Integer pageSize) {
        
        if(page < 0) {
            throw new InvalidRequestException(Contants.POSITIVE_PAGE_INDEX());
        }

        if(pageSize < 0) {
            throw new InvalidRequestException(Contants.POSITIVE_PAGE_SIZE());
        } 

        if(pageSize > Contants.MAX_PAGE_SIZE_PARAM) {
            throw new InvalidRequestException(Contants.MAX_PAGE_SIZE);
        }

    }
}
