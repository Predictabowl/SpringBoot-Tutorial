package com.examples.spring.demo.controllers;



import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collections;
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
import com.examples.spring.demo.model.EmployeeDTO;
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
	public void test_homeView_show_employees() throws Exception {
		List<Employee> employees = Arrays.asList(new Employee(1L, "Mario", 1000));
		
		when(employeeService.getAllEmployees()).thenReturn(employees);
		
		mvc.perform(get("/"))
			.andExpect(view().name("index"))
			.andExpect(model().attribute("employees", employees))
			.andExpect(model().attribute("message", ""));
	}
	
	@Test
	public void test_homeView_when_there_isnt_any_employee() throws Exception {
		when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());
		
		mvc.perform(get("/"))
			.andExpect(view().name("index"))
			.andExpect(model().attribute("employees", Collections.emptyList()))
			.andExpect(model().attribute("message", "There are no employees"));
	}

	@Test
	public void test_editEmployee_success() throws Exception {
		Employee employee = new Employee(1L,"Mario",1000);
		
		when(employeeService.getEmployeeById(1L)).thenReturn(employee);
		
		mvc.perform(get("/edit/1"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("employee", employee))
			.andExpect(model().attribute("message", ""));
	}
	
	@Test
	public void test_editEmployee_not_found() throws Exception {
		when(employeeService.getEmployeeById(1L)).thenReturn(null);
		
		mvc.perform(get("/edit/1"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("employee", nullValue()))
			.andExpect(model().attribute("message", "No employee found with id: 1"));
	}
	
	@Test
	public void test_newEmployee() throws Exception {
		mvc.perform(get("/new"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("employee", new Employee()))
			.andExpect(model().attribute("message", ""));
		
		verifyZeroInteractions(employeeService);
	}
	
	@Test
	public void test_PostEmployee_without_it_should_insert_new_one() throws Exception {
		mvc.perform(post("/save").param("name", "Luigi").param("salary", "1250"))
			.andExpect(view().name("redirect:/"));//this will go to the main page
		
		verify(employeeService).insertNewEmployee(new EmployeeDTO(null, "Luigi", 1250L));
		verifyNoMoreInteractions(employeeService);
	}
	
	@Test
	public void test_PostEmployee_with_id_should_edit() throws Exception {
		mvc.perform(post("/save")
				.param("id", "1")
				.param("name", "Carlo")
				.param("salary", "1250"))
			.andExpect(view().name("redirect:/"));
		
		verify(employeeService).updateEmployeeById(1L, new EmployeeDTO(1L,"Carlo",1250L));
		verifyNoMoreInteractions(employeeService);
	}
}
