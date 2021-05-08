package br.com.autoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String homeAdmin(){
        return "/admin/admin";
    }
}
