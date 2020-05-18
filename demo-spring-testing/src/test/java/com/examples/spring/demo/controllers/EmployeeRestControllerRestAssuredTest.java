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

import com.examples.spring.demo.model.Employee;
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

}
