package com.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.entity.Employee;
import com.notification.errors.EmployeeNotFoundException;
import com.notification.model.EmployeeForm;
import com.notification.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("")
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(this.employeeRepository.findAll());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Employee> update(@PathVariable("id") Integer id, @RequestBody EmployeeForm form) {
		Employee existed = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
		existed.setFirstName(form.getName());
		this.employeeRepository.save(existed);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		Employee existed = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
		this.employeeRepository.delete(existed);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
