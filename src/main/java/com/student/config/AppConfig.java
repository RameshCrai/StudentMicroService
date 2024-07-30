package com.student.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
//	@Bean
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
	
//	don't use this kind of hard code 
//	@Value("${addressservice.base.url}")
//	private String addressBaseURL;
//	
//	@Bean
//	public WebClient webClient() {
//		return WebClient
//				.builder()
//				.baseUrl(addressBaseURL)
//				.build();
//				
//	}

}
