package br.com.autoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/home")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("index.html");
		return modelAndView;
	}
}
