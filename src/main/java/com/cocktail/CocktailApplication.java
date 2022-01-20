package com.cocktail;

import lombok.extern.log4j.Log4j2;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Log4j2
@SpringBootApplication
@EnableScan
public class CocktailApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocktailApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(Environment environment) {
		return args -> {
			log.info("app props" + environment.getProperty("message from env yaml"));
		};
	}
}
