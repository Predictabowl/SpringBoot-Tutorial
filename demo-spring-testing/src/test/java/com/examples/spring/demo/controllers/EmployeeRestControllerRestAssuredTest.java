package com.examples.spring.demo.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.services.EmployeeService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;


/**
 * Alternative way to test the RestController using RestAssured
 * @author findus
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRestControllerRestAssuredTest {
	
	private static final String REST_PATH = "/api/employees";

	@Mock
	private EmployeeService employeeService;
	
	@InjectMocks
	private EmployeeRestController employeeRestController;
	
	@Before
	public void setUp() {
		RestAssuredMockMvc.standaloneSetup(employeeRestController);
	}
	
	@Test
	public void test_oneEmployee_success() {
		when(employeeService.getEmployeeById(1))
			.thenReturn(new Employee(1L, "Carlo", 1100));
		
		// this is using RestAssuredMockMvc,
		// the standard RestAssure can't work without the server running 
		given()
		.when()
			.get(REST_PATH+"/1")
		.then()
			.statusCode(200)
			.assertThat()
				.body("id", equalTo(1),
					"name",equalTo("Carlo"),
					"salary", equalTo(1100));
		
		verify(employeeService,times(1)).getEmployeeById(1);
	}
	
	@Test
	public void test_postEmployee() {
		Employee newEmployee = new Employee(null, "new Employee", 1500);
		when(employeeService.insertNewEmployee(newEmployee))
			.thenReturn(new Employee(1L, "new Employee", 1500));
		
		given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(newEmployee)
		.when()
			.post(REST_PATH+"/new")
		.then()
			.statusCode(200)
			.assertThat()
				.body("id",equalTo(1),
					"name", equalTo("new Employee"),
					"salary", equalTo(1500));
	}
	
	@Test
	public void test_updateEmployee_success() {
		Employee replacement = new Employee(null, "replacement", 1200);
		when(employeeService.updateEmployeeById(1L, replacement))
			.thenReturn(new Employee(1L, "replacement", 1200));
		
		given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(replacement)
		.when()
			.put(REST_PATH+"/update/1")
		.then()
			.statusCode(200)
			.assertThat()
				.body("id",equalTo(1),
					"name", equalTo("replacement"),
					"salary", equalTo(1200));
	}

}
