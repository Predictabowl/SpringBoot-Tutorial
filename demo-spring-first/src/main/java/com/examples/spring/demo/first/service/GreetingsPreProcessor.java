package com.examples.spring.demo.first.service;

import org.springframework.stereotype.Component;

@Component
public class GreetingsPreProcessor {
	String preProcess(String s) {
		return s;
	}
}
