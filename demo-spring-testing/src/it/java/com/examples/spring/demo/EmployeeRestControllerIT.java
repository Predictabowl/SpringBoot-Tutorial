package com.examples.spring.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.repositories.EmployeeRepository;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Some examples of tests for the rest controller when running in a real web
 * container, manually using the {@link EmployeeRepository}
 * 
 * The web server is started on a random port, which can be retrieved by
 * injecting in the test a {@link LocalServerPort}
 * 
 * In test you can't rely on fixed identifiers: use the ones returner by the
 * repository after saving (automatically generated)
 * 
 * @author findus
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeRestControllerIT {

	private static final String REST_ROOT = "/api/employees";
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@LocalServerPort
	private int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
		// always start with an empty database
		employeeRepository.deleteAll();
		employeeRepository.flush();
	}
	
	@Test
	public void test_new_employee() throws Exception{
		// we create a new employee using the POST rest end-point
		Response postResponse = given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new Employee(null, "Mario", 1000))
		.when()
			.post(REST_ROOT+"/new");
		
		Employee saved = postResponse.getBody().as(Employee.class);
		
		assertThat(employeeRepository.findById(saved.getId()).get())
			.isEqualTo(saved);
	}
	
	@Test
	public void test_update_employee() throws Exception{
		Employee saved = employeeRepository.save(new Employee(null, "Mario", 1250));
		
		given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new Employee(null, "Luigi", 1000))
		.when()
			.put(REST_ROOT+"/update/"+saved.getId())
		.then()
			.statusCode(200)
			.assertThat()
				.body("id", equalTo(saved.getId().intValue()),
					"name", equalTo("Luigi"),
					"salary", equalTo(1000));
			
	}
}
