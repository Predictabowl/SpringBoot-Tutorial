package com.examples.spring.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.repositories.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeWebControllerIT {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private WebDriver driver;

	private String baseUrl;

	@LocalServerPort
	private int port;

	@Before
	public void setUp() {
		baseUrl = "http://localhost:"+port;
		driver = new HtmlUnitDriver();
		employeeRepository.deleteAll();
		employeeRepository.flush();
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void test_homePage() {
		Employee employee = employeeRepository.save(new Employee(null, "test employee", 1000));
		
		driver.get(baseUrl);
		
		assertThat(driver.findElement(By.id("employee_table")).getText())
			.contains(employee.getId().toString(),"test employee","1000","Edit");
		
		driver.findElement(By.cssSelector("a[href*='/edit/"+employee.getId()+"']"));
	}
	
	@Test
	public void test_edit_page_new_employee() {
		driver.get(baseUrl+"/new");
		
		driver.findElement(By.name("name")).sendKeys("new employee");
		driver.findElement(By.name("salary")).sendKeys("1200");
		driver.findElement(By.name("btn_submit")).click();
		
		assertThat(employeeRepository.findByName("new employee").getSalary()).isEqualTo(1200L);
	}
	
	@Test
	public void test_edit_page_update_employee() {
		Employee employee = employeeRepository.save(new Employee(null, "old employee", 1300));
		
		driver.get(baseUrl+"/edit/"+employee.getId());
		
		final WebElement nameField = driver.findElement(By.name("name"));
		nameField.clear();
		nameField.sendKeys("modified employee");
		final WebElement salaryField = driver.findElement(By.name("salary"));
		salaryField.clear();
		salaryField.sendKeys("1250");
		driver.findElement(By.name("btn_submit")).click();
		
		assertThat(employeeRepository.findByName("modified employee").getSalary()).isEqualTo(1250L);
	}
}
