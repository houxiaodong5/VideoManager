package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.example")
@EnableAutoConfiguration
@EnableScheduling
public class VideoManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoManagerApplication.class, args);
	}

}
