package com.examples.spring.demo.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.examples.spring.demo.model.Employee;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeRestController.class)
public class EmployeeRestControllerTest {

	private static final String API_EMPLOYEES = "/api/employees";

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	@Test
	public void test_allEmployees_is_empty() throws Exception {
		this.mvc.perform(get(API_EMPLOYEES)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
	}
	
	@Test
	public void teset_allEmployees_not_empty() throws Exception {
		when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(
				new Employee(1L, "first", 1000),
				new Employee(2L, "second", 2000)));
		
		this.mvc.perform(get(API_EMPLOYEES).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("first")))
			.andExpect(jsonPath("$[0].salary", is(1000)))
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].name", is("second")))
			.andExpect(jsonPath("$[1].salary", is(2000)));
	}
	
	@Test
	public void test_oneEmployee_found() throws Exception {
		when(employeeService.getEmployeeById(anyLong()))
			.thenReturn(new Employee(1L, "first", 1250));
		
		this.mvc.perform(get(API_EMPLOYEES+"/1").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("first")))
			.andExpect(jsonPath("$.salary", is(1250)));
	}
	
	@Test
	public void test_oneEmployee_not_found() throws Exception{
		when(employeeService.getEmployeeById(anyLong()))
			.thenReturn(null);
		
		this.mvc.perform(get(API_EMPLOYEES+"/2").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(""));
	}
	

}
