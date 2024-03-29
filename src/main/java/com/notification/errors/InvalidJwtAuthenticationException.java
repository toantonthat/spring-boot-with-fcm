package com.notification.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidJwtAuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidJwtAuthenticationException() {
	}

	public InvalidJwtAuthenticationException(String message) {
		super(message);
	}
}
