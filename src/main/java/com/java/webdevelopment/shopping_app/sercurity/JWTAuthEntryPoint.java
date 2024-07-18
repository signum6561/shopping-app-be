package com.java.webdevelopment.shopping_app.sercurity;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {
    
	@Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException, java.io.IOException {
		resolver.resolveException(request, response, null, e);
	}
}
