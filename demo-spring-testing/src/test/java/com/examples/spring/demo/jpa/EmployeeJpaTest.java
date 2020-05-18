package com.examples.spring.demo.jpa;


import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.demo.model.Employee;

@DataJpaTest
@RunWith(SpringRunner.class)
public class EmployeeJpaTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void test_jpa_mapping() {
		Employee employee = entityManager.persistFlushFind(new Employee(null, "Mario", 5000));
		
		assertThat(employee.getName()).isEqualTo("Mario");
		assertThat(employee.getSalary()).isEqualTo(5000);
		assertThat(employee.getId()).isNotNull();
		assertThat(employee.getId()).isGreaterThan(0);
		
		LoggerFactory.getLogger(EmployeeJpaTest.class).info("Saved employee: "+employee.toString());
	}
}
