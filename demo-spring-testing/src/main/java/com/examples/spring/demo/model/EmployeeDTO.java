package com.examples.spring.demo.model;

public class EmployeeDTO extends Employee{
	
	public EmployeeDTO() {
	}
	
	public EmployeeDTO(Long id, String name, long salary) {
		super(id, name, salary);
	}
	
	public Employee mapToEmployee() {
		return new Employee(getId(), getName(), getSalary());
	}

}
