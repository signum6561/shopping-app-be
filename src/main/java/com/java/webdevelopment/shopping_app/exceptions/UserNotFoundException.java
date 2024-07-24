package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class UserNotFoundException extends ResourcesNotFoundException {
    
	public UserNotFoundException() {
		super(Contants.DEFAULT_NOT_FOUND("user"));
	}

    public UserNotFoundException(String username) {
		super(Contants.USER_NOT_FOUND(username));
	}

	@Override
	public String getResourceName() {
		return "user";
	}
}
