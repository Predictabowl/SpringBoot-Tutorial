package com.examples.spring.demo.services;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.examples.spring.demo.model.Employee;

/**
 * Fake implementation to make some manual tests.
 * Won't be used on automated tests since it will be mocked.
 *  
 * @author findus
 *
 */

@Service
public class EmployeeService {

	private Map<Long, Employee> employees = new LinkedHashMap<Long, Employee>();
	
	public EmployeeService() {
		employees.put(1L, new Employee(1L,"Carlo Pedersoli",2000));
		employees.put(2L, new Employee(1L,"Mario Girotti",1950));		
	}

	public List<Employee> getAllEmployees() {
		return new LinkedList<Employee>(employees.values());
	}

	public Employee getEmployeeById(long id) {
		return employees.get(id);
	}

	public Employee insertNewEmployee(Employee employee) {
		employee.setId(employees.size()+1L);
		employees.put(employee.getId(), employee);
		return employee;
	}

	public Employee updateEmployeeById(long id, Employee employee) {
		employee.setId(id);
		employees.put(id, employee);
		return employee;
	}

}
