package com.fptu.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CapstoneApplication implements WebMvcConfigurer{

	public static void main(String[] args) {

		SpringApplication.run(CapstoneApplication.class, args);
		System.out.println("CapstoneApplication.main");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/content/**")
				.addResourceLocations("file:///" + System.getProperty("user.dir") + "/src/main/content/");
	}

}
