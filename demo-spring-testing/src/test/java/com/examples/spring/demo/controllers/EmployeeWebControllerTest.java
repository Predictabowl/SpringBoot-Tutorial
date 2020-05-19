package com.examples.spring.demo.controllers;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.services.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeWebController.class)
public class EmployeeWebControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	@Test
	public void test_status_successful() throws Exception {
		mvc.perform(get("/")).andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void test_return_home_view() throws Exception {
		ModelAndViewAssert.assertViewName(mvc.perform(get("/"))
				.andReturn().getModelAndView(), "index");
	}
	
	@Test
	public void test_HomeView_show_employees() throws Exception {
		List<Employee> employees = Arrays.asList(new Employee(1L, "Mario", 1000));
		
		when(employeeService.getAllEmployees()).thenReturn(employees);
		
		mvc.perform(get("/"))
			.andExpect(view().name("index"))
			.andExpect(model().attribute("employees", employees));
	}

}
