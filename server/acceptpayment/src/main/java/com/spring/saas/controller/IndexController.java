package com.spring.saas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2019-10-05 23:36
 * @author dell
 */
@Controller
public class IndexController {

	@GetMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
}
