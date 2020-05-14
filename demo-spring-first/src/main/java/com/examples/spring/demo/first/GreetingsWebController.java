package com.examples.spring.demo.first;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingsWebController {

	@RequestMapping("/")
	public String welcome (Map<String, Object> model) {
		model.put("message", "World");
		// In this case index is a HTML page, so will search for index.html
		return "index";
	}
}
