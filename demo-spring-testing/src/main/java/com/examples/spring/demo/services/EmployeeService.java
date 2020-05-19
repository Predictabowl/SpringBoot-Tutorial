package com.examples.spring.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examples.spring.demo.model.Employee;

@Service
public class EmployeeService {

	private static final String TEMPORARY_IMPLEMENTATION = "Temporary Implementation";

	public List<Employee> getAllEmployees() {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

}
