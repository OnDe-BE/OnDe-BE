package com.ott.onde;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //modifiedAt, createdAt 생성을 위해 필요한 어노테이션
public class OndeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OndeApplication.class, args);
	}

}
