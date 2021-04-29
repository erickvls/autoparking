package br.com.autoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/hello2")
    public String hello2(){
        return "helloword2";
    }

    @GetMapping("/hello3")
    public String hello3(){
        return "helloword2";
    }

    @GetMapping("/")
    public String home(){
        return "helloword2";
    }

}
