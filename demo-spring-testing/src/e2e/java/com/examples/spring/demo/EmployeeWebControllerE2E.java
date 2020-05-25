package com.examples.spring.demo;

import static org.assertj.core.api.Assertions.assertThat;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.examples.spring.demo.functionaltests.ChromeSeleniumDriver;
import com.examples.spring.demo.pageobject.EditPage;
import com.examples.spring.demo.pageobject.IndexPage;


public class EmployeeWebControllerE2E extends ChromeSeleniumDriver{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeWebControllerE2E.class);

	@Test
	public void test_homePage() {
		driver.get(baseUrl);
		IndexPage indexPage = new IndexPage(driver);
		
		assertThat(indexPage.getNewEmployeeLink()).isNotNull();
	}
	
	@Test
	public void test_create_new_employee() {
		driver.get(baseUrl);
		IndexPage indexPage = new IndexPage(driver);
		indexPage.clickNewEmployee();
		
		EditPage editPage = new EditPage(driver);
		editPage.typeName("new Employee");
		editPage.typeSalary("2000");
		editPage.clickSubmitButton();
		
		assertThat(indexPage.getEmployeeTable().getText())
			.contains("new Employee","2000");
	}

	@Test
	public void test_edit_employee() throws JSONException {
		// First we put an employee into the DB using the REST API
		String id = postEmployee("employee to edit", 3000);
		
		driver.get(baseUrl);
		IndexPage indexPage = new IndexPage(driver);
		indexPage.getEditEmployeeLink(id).click();
		
		EditPage editPage = new EditPage(driver);
		editPage.typeName("modified employee");
		editPage.typeSalary("2000");
		editPage.clickSubmitButton();
		
		assertThat(indexPage.getEmployeeTable().getText())
			.contains(id,"modified employee","2000");
	}
	
	/**
	 * We use directly the REST end point to POST an employee into the Database.
	 * 
	 * @param name
	 * @param salary
	 * @return
	 * @throws JSONException
	 */
	private String postEmployee(String name, long salary) throws JSONException {
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("salary", salary);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> answer = new RestTemplate().postForEntity(baseUrl+"/api/employees/new", entity, String.class);

		LOGGER.debug("answer for POST: "+answer);
		return new JSONObject(answer.getBody()).get("id").toString();
	}
}
