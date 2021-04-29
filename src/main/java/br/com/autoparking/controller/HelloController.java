package br.com.autoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello2")
    public String hello(Model model){
        model.addAttribute("message","Hello");
        return "helloword2";
    }

    @GetMapping("/hello3")
    public String hello(){
        return "<h1>helloword3</h1>";
    }
}
