package com.examples.spring.demo.repositories;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.demo.model.Employee;

/**
 * this is a learning test
 * @author findus
 *
 */

@DataJpaTest
@RunWith(SpringRunner.class)
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void first_learning_test() {
		Employee employee = new Employee(null, "test", 1000);
		Employee saved = repository.save(employee);
		Collection<Employee> employees = repository.findAll();
		assertThat(employees).containsExactly(saved);
	}
	
	/*
	 * We can add a record with the TestEntityManager
	 * and read it back with re repository
	 */
	@Test
	public void second_learning_test() {
		Employee employee = new Employee(null, "test", 1500);
		Employee saved = entityManager.persistFlushFind(employee);
		
		Collection<Employee> employees = repository.findAll();
		assertThat(employees).containsExactly(saved);
	}
	
	@Test
	public void test_findByName_employee() {
		Employee employee = new Employee(null, "test", 1500);
		Employee saved = entityManager.persistFlushFind(employee);
		
		Employee found = repository.findByName("test");
		
		assertThat(found).isSameAs(saved);
	}
	
	@Test
	public void test_find_by_name_and_salary() {
		Employee saved1 = entityManager.persistFlushFind(new Employee(null, "Tizio", 1000));
		entityManager.persistFlushFind(new Employee(null, "Caio", 2000));
		entityManager.persistFlushFind(new Employee(null, "Tizio", 3000));
		
		Employee found = repository.findByNameAndSalary("Tizio",1000L);
		
		assertThat(found).isSameAs(saved1);
	}
	
	@Test
	public void test_find_by_name_or_salary() {
		Employee saved1 = entityManager.persistFlushFind(new Employee(null, "Tizio", 1000));
		Employee saved2 = entityManager.persistFlushFind(new Employee(null, "Caio", 2000));
		entityManager.persistFlushFind(new Employee(null, "Sempronio", 3000));
		
		List<Employee> found = repository.findByNameOrSalary("Tizio",2000L);
		
		assertThat(found).containsExactly(saved1,saved2);
	}
	
	@Test
	public void test_findAll_employees_within_cap_salary() {
		Employee saved1 = entityManager.persistFlushFind(new Employee(null, "Tizio", 1000));
		Employee saved2 = entityManager.persistFlushFind(new Employee(null, "Caio", 2000));
		entityManager.persistFlushFind(new Employee(null, "Sempronio", 3000));
		
		List<Employee> found = repository.findAllEmployeesWithinSalaryCap(2100L);
		
		assertThat(found).containsExactly(saved1,saved2);
	}

}
