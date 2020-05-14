package com.examples.spring.demo.first.service;

import org.springframework.stereotype.Component;

@Component
public class GreetingsServiceDefaultImpl implements GreetingsService{

	@Override
	public String getGreeting() {
		return "Hello";
	}

}
