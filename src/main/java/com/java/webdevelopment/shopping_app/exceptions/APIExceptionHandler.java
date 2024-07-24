package com.java.webdevelopment.shopping_app.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.payload.responses.ErrorResponse;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleAlreadyExist(AlreadyExistsException e) {
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.CONFLICT);
		response.setType("unique-field");
		response.addError(e.getField(), e.getMessage());
		return response;
	}

	@ExceptionHandler(ResourcesNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleUserNotFound(ResourcesNotFoundException e){
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setType("not-found");
		response.addError(e.getResourceName(), e.getMessage());
		return response;
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handleUnauthorized(AuthenticationException e) {
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.UNAUTHORIZED);
		response.setType("authencation");
		response.addDefaultError(e.getMessage());
		return response;
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponse handleAccessDenied(AuthenticationException e) {
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.FORBIDDEN);
		response.setType("authorization");
		response.addDefaultError(e.getMessage());
		return response;
	}

	@ExceptionHandler(InvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalidRequest(InvalidRequestException e) {
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setType("invalid-content");
		response.addDefaultError(e.getMessage());
		return response;
	}

	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setType("validation");
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
			response.addError(fieldName, errorMessage);
		});
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(AuthorizationDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponse handleAuthDenied(AuthorizationDeniedException e){
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.FORBIDDEN);
		response.setType("authorization");
		response.addDefaultError(Contants.ACCESS_DENIED);
		return response;
	}

	@ExceptionHandler(ModifyBaseRoleException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleAuthDenied(ModifyBaseRoleException e){
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.CONFLICT);
		response.setType("action-denied");
		response.addDefaultError(Contants.ACCESS_DENIED);
		return response;
	}

	@ExceptionHandler(SystemAdminDeleteException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleSelfDeleteAdmin(SystemAdminDeleteException e){
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.CONFLICT);
		response.setType("invalid-action");
		response.addDefaultError(e.getMessage());
		return response;
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleServerError(RuntimeException e){
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setType("server-error");
		response.addDefaultError(e.getMessage());
		return response;
	}
}
