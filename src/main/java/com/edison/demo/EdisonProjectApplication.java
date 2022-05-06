package com.edison.demo;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.edison.demo.dao")
@SpringBootApplication
@EnableCaching
public class EdisonProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdisonProjectApplication.class, args);
	}

	@PostConstruct
    void started() {
      TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}
}
