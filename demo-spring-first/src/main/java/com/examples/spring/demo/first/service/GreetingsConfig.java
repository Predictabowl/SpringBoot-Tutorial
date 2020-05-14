package com.examples.spring.demo.first.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingsConfig {
	
	/* the name of the method HAS to be this one, it won't
	 * work if the name is changed
	 */
	@Bean
	public GreetingsService greetingsService(GreetingsPreProcessor preProcessor) {
		return new GreetingsServiceDefaultImpl(preProcessor);
	}

}
