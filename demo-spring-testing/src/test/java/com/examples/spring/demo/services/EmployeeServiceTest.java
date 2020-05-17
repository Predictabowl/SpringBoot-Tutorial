package com.examples.spring.demo.services;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*; 

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.repository.EmployeeRepository;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;
	
	private EmployeeService employeeService;
	
	@Before
	public void setUp() {
		employeeService = new EmployeeService(employeeRepository);
		
	}
	
	@Test
	public void test_getAllEmployees() {
		Employee employee1 = new Employee(1L, "first", 1000);
		Employee employee2 = new Employee(2L, "second", 5000);
		
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1,employee2));
		
		assertThat(employeeService.getAllEmployees()).containsExactly(employee1,employee2);
	}
	
	@Test
	public void test_getEmployeeById_success() {
		Employee employee = new Employee(1L, "someone", 1200);
		
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		
		assertThat(employeeService.getEmployeeById(1)).isEqualTo(employee);
	}
	
	@Test
	public void test_getEmployeeById_when_not_found() {
		when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		assertThat(employeeService.getEmployeeById(1)).isNull();
	}
	
	@Test
	public void test_insertNewEmployee_sets_id_to_null_and_returns_employee() {
		Employee toSave = spy(new Employee(99L, "", 0));
		Employee saved = new Employee(1L, "saved guy", 1000);
		
		when(employeeRepository.save(toSave)).thenReturn(saved);
		
		Employee returned = employeeService.insertNewEmployee(toSave);
		
		assertThat(returned).isSameAs(saved);
		
		InOrder inOrder = inOrder(toSave, employeeRepository);
		inOrder.verify(toSave).setId(null);
		inOrder.verify(employeeRepository).save(toSave);
	}
	
	@Test
	public void test_updateEmployeeById_sets_the_id_and_return_employee() {
		Employee replacement = spy(new Employee(null, "new guy", 0));
		Employee replaced = new Employee(1L, "old guy", 1000);
		
		when(employeeRepository.save(any(Employee.class))).thenReturn(replaced);
		
		Employee returned = employeeService.updateEmployeeById(1L,replacement);
		
		assertThat(returned).isSameAs(replaced);
		
		InOrder inOrder = inOrder(replacement, employeeRepository);
		inOrder.verify(replacement).setId(1L);
		inOrder.verify(employeeRepository).save(replacement);
	}

}
