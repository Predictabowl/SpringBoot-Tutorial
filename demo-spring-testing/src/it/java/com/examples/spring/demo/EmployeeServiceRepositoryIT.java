package com.examples.spring.demo;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.model.EmployeeDTO;
import com.examples.spring.demo.repositories.EmployeeRepository;
import com.examples.spring.demo.services.EmployeeService;

/**
 * An integration to verify the interactions between service and repository.
 * 
 * @author findus
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(EmployeeService.class)
public class EmployeeServiceRepositoryIT {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void test_service_should_insert_into_repository() {
		Employee savedEmployee = employeeService.insertNewEmployee(new EmployeeDTO(null, "Mario", 1000));
		
		assertThat(employeeRepository.findById(savedEmployee.getId())).isPresent();
	}

	@Test
	public void test_service_can_update_repository() {
		Employee savedEmployee = employeeRepository.save(new Employee(null, "Carlo", 1000));
		
		Employee modEmployee = employeeService.updateEmployeeById(
				savedEmployee.getId(), new EmployeeDTO(null, "Mario", 1250));
		
		assertThat(employeeRepository.findById(savedEmployee.getId()).get())
			.isEqualTo(modEmployee);
	}
	
}
