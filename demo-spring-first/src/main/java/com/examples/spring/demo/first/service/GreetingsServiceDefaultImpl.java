package com.examples.spring.demo.first.service;

import org.springframework.stereotype.Component;

@Component
public class GreetingsServiceDefaultImpl implements GreetingsService{

	private GreetingsPreProcessor preProcessor;
	
	public GreetingsServiceDefaultImpl(GreetingsPreProcessor preProcessor) {
		this.preProcessor = preProcessor;
	}

	@Override
	public String getGreeting() {
		return preProcessor.preProcess("Hello");
	}

}
