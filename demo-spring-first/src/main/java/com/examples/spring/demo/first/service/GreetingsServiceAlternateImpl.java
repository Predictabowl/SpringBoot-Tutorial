package com.examples.spring.demo.first.service;

import org.springframework.stereotype.Component;

@Component
public class GreetingsServiceAlternateImpl implements GreetingsService{

	@Override
	public String getGreeting() {
		return "MARIO";
	}

}
