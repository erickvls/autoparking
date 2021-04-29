package br.com.autoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Hello2Controller {

    @GetMapping("/hello2")
    public String hello2(Model model){
        return "helloword2";
    }

}
