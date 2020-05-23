package com.examples.spring.demo.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditPage extends PageObject{

	@FindBy(name = "name")
	private WebElement nameInput;
	
	@FindBy(name = "salary")
	private WebElement salaryInput;
	
	@FindBy(name = "btn_submit")
	private WebElement submitButton;
	
	public EditPage(WebDriver driver) {
		super(driver);
	}
	
	public void clickSubmitButton() {
		submitButton.click();
	}
	
	public void typeName(CharSequence name) {
		nameInput.clear();
		nameInput.sendKeys(name);
	}
	
	public void typeSalary(CharSequence salary) {
		salaryInput.clear();
		salaryInput.sendKeys(salary);
	}

}
