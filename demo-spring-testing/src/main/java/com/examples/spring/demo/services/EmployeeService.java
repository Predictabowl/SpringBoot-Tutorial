package com.examples.spring.demo.services;

import java.util.List;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.repositories.EmployeeRepository;

public class EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(long id) {	
		return employeeRepository.findById(id).orElse(null);
	}

	public Employee insertNewEmployee(Employee employee) {
		employee.setId(null);
		return employeeRepository.save(employee);
	}

	public Employee updateEmployeeById(long id, Employee employee) {
		employee.setId(id);
		return employeeRepository.save(employee);
	}
	
}
