package com.rapidapi.core.dss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DssApplication {

	public static void main(String[] args) {
		SpringApplication.run(DssApplication.class, args);
	}

}
