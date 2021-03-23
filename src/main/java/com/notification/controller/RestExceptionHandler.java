package com.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.notification.errors.EmployeeNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

	@ExceptionHandler(value = {EmployeeNotFoundException.class})
	public ResponseEntity<Object> vehicleNotFound(EmployeeNotFoundException exeption, WebRequest request) {
		log.debug("handling VehicleNotFoundException...");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
