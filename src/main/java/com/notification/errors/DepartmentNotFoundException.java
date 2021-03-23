package com.notification.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value =  HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DepartmentNotFoundException() {
	}

	public DepartmentNotFoundException(String departmentId) {
		super("Department: " + departmentId + " not found.");
	}
}
