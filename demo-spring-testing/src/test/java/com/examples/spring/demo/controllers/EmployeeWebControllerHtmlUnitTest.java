package com.examples.spring.demo.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.services.EmployeeService;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeWebController.class)
public class EmployeeWebControllerHtmlUnitTest {

	@Autowired
	private WebClient webClient;
	
	
	/* even if not used anywhere, the service must be mocked because we is needed
	 * by EmployeeWebControllewr  
	 */
	@MockBean
	private EmployeeService employeeService;
	
	@Test
	public void test_homePage_title() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlPage page = webClient.getPage("/");
		
		assertThat(page.getTitleText()).isEqualTo("Employees");
	}
	
	@Test
	public void test_homePage_without_employees() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());
		
		HtmlPage page = webClient.getPage("/");
		
		assertThat(page.getBody().getTextContent()).contains("There are no employees");
		
	} 
	
	@Test
	public void test_homePage_with_Employees_should_show_them_in_table() throws Exception{
		when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(
				new Employee(1L, "Mario", 1000),
				new Employee(2L, "Luigi", 1500)));
		
		HtmlPage page = webClient.getPage("/");
		HtmlTable table = page.getHtmlElementById("employee_table");
				
		assertThat(page.getBody().getTextContent()).doesNotContain("no employees");
		assertThat(removeWindowsCR(table.asText())).isEqualTo(
				"ID	Name	Salary\n"
				+"1	Mario	1000\n"
				+"2	Luigi	1500");
	}
	
	@Test
	public void test_edit_non_existent_employee() throws Exception{
		when(employeeService.getEmployeeById(1L)).thenReturn(null);
		
		HtmlPage page = webClient.getPage("/edit/1");
		
		assertThat(page.getTitleText()).isEqualTo("Edit Employee");
		assertThat(page.getBody().getTextContent()).contains("No employee found with id: 1");
	}
	
	private String removeWindowsCR(String s) {
		return s.replaceAll("\r", "");
	}
}
