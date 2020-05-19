package com.examples.spring.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.examples.spring.demo.model.Employee;
import com.examples.spring.demo.services.EmployeeService;

@Controller
public class EmployeeWebController {
	
	private static final String MESSAGE_ATTRIBUTE = "message";
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/")
	public String index(Model model) {
		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("employees",employees);
		String message;
		if(employees.isEmpty())
			message = "There are no employees";
		else
			message = "";
		model.addAttribute(MESSAGE_ATTRIBUTE,message);
		return "index";
	}
	
	@GetMapping("/edit/{id}")
	public String editEmployee(@PathVariable long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee",employee);
		String message;
		if(employee == null)
			message = "No employee found with id: "+id;
		else
			message = "";
		model.addAttribute(MESSAGE_ATTRIBUTE,message);
		
		return "edit";
	}
	
	@GetMapping("/new")
	public String newEmployee(Model model) {
		model.addAttribute("employee",new Employee());
		model.addAttribute(MESSAGE_ATTRIBUTE,"");
		return "edit";
	}
	
	@PostMapping("/save")
	public String saveEmployee(Employee employee) {
		employeeService.insertNewEmployee(employee);
		return "redirect:/";
	}
		
}
