package br.com.autoparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.sql.SQLOutput;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.autoparking")
public class AutoparkingApplication {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("SENHA"+bCryptPasswordEncoder.encode("erickvls"));
        System.err.println("SENHA HEROKU:"+System.getenv("SENHA_GMAIL"));
        SpringApplication.run(AutoparkingApplication.class, args);
        System.err.println("SENHA HEROKU:"+System.getenv("SENHA_GMAIL"));
    }
}
