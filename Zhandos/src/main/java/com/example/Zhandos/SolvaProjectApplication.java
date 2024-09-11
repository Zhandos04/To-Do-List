package com.example.Zhandos;

import com.example.Zhandos.API.Holidays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SolvaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolvaProjectApplication.class, args);
		Holidays holidays = new Holidays();
		holidays.holiday();
	}
}
