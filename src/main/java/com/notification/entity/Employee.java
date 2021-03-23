package com.notification.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.notification.enums.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "emp_no", length = 11, nullable = false)
	private int id;

	@Column(name = "birth_date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
	private Instant birthDate;

	@Column(name = "first_name", updatable = true, nullable = false, columnDefinition = "VARCHAR(14) DEFAULT 'NULL'")
	private String firstName;
	
	@Column(name = "last_name", updatable = true, nullable = true, columnDefinition = "VARCHAR(16)")
	private String lastName;
	
	@Column(name = "gender", nullable = false, columnDefinition = "enum('M','F')")
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;
	
	@Column(name = "hire_date", nullable = true, columnDefinition = "DATE DEFAULT CURRENT_DATE")
	private Instant hireDate;
	
//	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
//	private List<Salary> salaries;
	
//	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
//	@Fetch(value = FetchMode.SUBSELECT)
//	private List<Title> titles;
}
