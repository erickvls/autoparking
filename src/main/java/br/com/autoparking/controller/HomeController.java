package br.com.autoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String login() {
		return "/index.html";
	}

	@RequestMapping(value = "/home2",method = RequestMethod.GET)
	public String login2() {
		return "index.html";
	}

	@RequestMapping(value = "/home3",method = RequestMethod.GET)
	public String login3() {
		return "index";
	}

	@RequestMapping(value = "/home4",method = RequestMethod.GET)
	public String login4() {
		return "/index";
	}

	@RequestMapping(value = "/home5",method = RequestMethod.GET)
	public ModelAndView login5() {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}

	@RequestMapping(value = "/home6",method = RequestMethod.GET)
	public ModelAndView login6() {
		ModelAndView mav = new ModelAndView("index.html");
		return mav;
	}

	@RequestMapping(value = "/home7",method = RequestMethod.GET)
	public ModelAndView login7() {
		ModelAndView mav = new ModelAndView("/index.html");
		return mav;
	}
}
