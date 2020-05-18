package com.examples.spring.demo.services;

import java.util.List;

import com.examples.spring.demo.model.Employee;

public class EmployeeService {

	private static final String TEMPORARY_IMPLEMENTATION = "Temporary Implementation";

	public List<Employee> getAllEmployees() {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}
	
	public Employee getEmployeeById(long id) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}
	
	public Employee insertNewEmployee(Employee employee) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	public Employee updateEmployeeById(long id, Employee employee) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

}
