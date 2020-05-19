package com.examples.spring.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.examples.spring.demo.services.EmployeeService;

@Controller
public class EmployeeWebController {
	
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("employees",employeeService.getAllEmployees());
		return "index";
	}
}
