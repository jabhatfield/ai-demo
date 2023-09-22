package com.jonhatfield.aidemo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "RAID OpenAPI Docs", version = "1.0", description = "RESTful AI Demo API Docs"))
@SpringBootApplication
public class AIDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AIDemoApplication.class, args);
	}
}