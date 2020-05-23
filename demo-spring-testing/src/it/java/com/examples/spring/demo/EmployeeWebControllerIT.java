package com.examples.spring.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.examples.spring.demo.functionaltests.RandomPortHtmlUnitDriver;
import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.pageobject.EditPage;
import com.examples.spring.demo.pageobject.IndexPage;
import com.examples.spring.demo.repositories.EmployeeRepository;

public class EmployeeWebControllerIT extends RandomPortHtmlUnitDriver{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Before
	public void setUpRepository() {
		employeeRepository.deleteAll();
		employeeRepository.flush();
	}
	
	
	@Test
	public void test_homePage() {
		Employee employee = employeeRepository.save(new Employee(null, "test employee", 1000));
		
		driver.get(baseUrl);		
		IndexPage indexPage = new IndexPage(driver);
		
		assertThat(indexPage.getEmployeeTable().getText())
			.contains(employee.getId().toString(),"test employee","1000","Edit");
		assertThat(indexPage.getEditEmployeeLink(employee.getId()).getText()).isEqualTo("Edit");
	}
	
	@Test
	public void test_edit_page_new_employee() {
		driver.get(baseUrl+"/new");
		
		EditPage editPage = new EditPage(driver);
		editPage.typeName("new employee");
		editPage.typeSalary("1200");
		editPage.clickSubmitButton();
		
		assertThat(employeeRepository.findByName("new employee").getSalary()).isEqualTo(1200L);
	}
	
	@Test
	public void test_edit_page_update_employee() {
		Employee employee = employeeRepository.save(new Employee(null, "old employee", 1300));
		
		driver.get(baseUrl+"/edit/"+employee.getId());
		
		EditPage editPage = new EditPage(driver);
		editPage.typeName("modified employee");
		editPage.typeSalary("1250");
		editPage.clickSubmitButton();
		
		assertThat(employeeRepository.findByName("modified employee").getSalary()).isEqualTo(1250L);
	}
	
}
