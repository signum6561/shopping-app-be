package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class UserNotFoundException extends ResourcesNotFoundException {
    public UserNotFoundException() {
		super(Contants.USER_NOT_FOUND);
	}

    public UserNotFoundException(String username) {
		super(Contants.USER_NOT_FOUND(username));
	}

	@Override
	public String getResource() {
		return "user";
	}
}
