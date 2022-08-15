package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lms.model.service.BookService;
import com.lms.model.service.EmployeeService;

@Controller
public class BasicController {

	@Autowired
	private BookService bs;
	
	@Autowired
	private EmployeeService es;
	
//	@RequestMapping("/")
//	public ModelAndView home() {
//		
//		return new ModelAndView("index.html");
//		
//	}
}
