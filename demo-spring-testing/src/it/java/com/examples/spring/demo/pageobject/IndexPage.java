package com.examples.spring.demo.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IndexPage extends PageObject{
	
	@FindBy(id = "employee_table")
	private WebElement employeeTable;
	
	@FindBy(css = "a[href*='/new'")
	private WebElement newEmployeeLink;
	
	public IndexPage(WebDriver driver) {
		super(driver);
	}
	
	public WebElement getEmployeeTable() {
		return employeeTable;
	}
	
	public WebElement getEditEmployeeLink(String id) {
		return driver.findElement(By.cssSelector("a[href*='/edit/"+id+"']"));
	}
	
	public WebElement getNewEmployeeLink() {
		return newEmployeeLink;
	}
	
	public void clickNewEmployee() {
		newEmployeeLink.click();
	}

}
