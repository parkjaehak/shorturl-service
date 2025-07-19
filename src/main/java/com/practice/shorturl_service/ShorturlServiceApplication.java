package com.practice.shorturl_service;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShorturlServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShorturlServiceApplication.class, args);
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Short URL Service API")
						.description("URL 단축 서비스 API 문서")
						.version("1.0.0")
						.contact(new Contact()
								.name("Short URL Service Team")
								.email("atom8426@naver.com")));
	}
}
