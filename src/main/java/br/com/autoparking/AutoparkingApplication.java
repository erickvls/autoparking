package br.com.autoparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"service","controller"})
public class AutoparkingApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutoparkingApplication.class, args);
	}
}
