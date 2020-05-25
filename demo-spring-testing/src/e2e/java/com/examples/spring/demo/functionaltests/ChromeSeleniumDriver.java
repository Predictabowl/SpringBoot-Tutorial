package com.examples.spring.demo.functionaltests;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeSeleniumDriver {
	
	private static int port = Integer.parseInt(System.getProperty("server.port", "8080"));
	protected ChromeDriver driver;
	protected String baseUrl;
	
	@BeforeClass
	public static void setUpClass() {
		
		WebDriverManager.chromedriver().setup();
	}
	
	@Before
	public void setUp() {
		baseUrl = "http://localhost:"+port;
		driver = new ChromeDriver();
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
}
