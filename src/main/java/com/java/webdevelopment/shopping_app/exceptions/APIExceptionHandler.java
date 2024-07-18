package com.java.webdevelopment.shopping_app.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.payload.responses.ErrorResponse;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleAlreadyExist(AlreadyExistsException e) {
		ErrorResponse response = new ErrorResponse(
				"unique-" + e.getField(),
				e.getMessage());
		return response;
	}

	@ExceptionHandler(ResourcesNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleUserNotFound(ResourcesNotFoundException e){
		ErrorResponse message = new ErrorResponse(
				e.getResource() + "-not-found",
				e.getMessage());
		return message;
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handleUnauthorized(AuthenticationException e) {
		ErrorResponse message = new ErrorResponse(
				"auth-001",
				Contants.UNAUTHORIZED);
		return message;
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponse handleAccessDenied(AuthenticationException e) {
		ErrorResponse message = new ErrorResponse(
				"access-denied",
				e.getMessage());
		return message;
	}

	@ExceptionHandler(InvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalidRequest(InvalidRequestException e) {
		ErrorResponse message = new ErrorResponse(
				"invalid-request",
				e.getMessage());
		return message;
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleServerError(RuntimeException e){
		ErrorResponse message = new ErrorResponse(
				"server",
				e.getMessage());
		return message;
	}
}
