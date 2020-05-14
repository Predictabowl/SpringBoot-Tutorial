package com.examples.spring.demo.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//whitout this annotation Autowire can't find the repository
@EnableJpaRepositories("com.examples.spring.demo.repository")
@SpringBootApplication
public class DemoSpringFirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringFirstApplication.class, args);
	}

}
