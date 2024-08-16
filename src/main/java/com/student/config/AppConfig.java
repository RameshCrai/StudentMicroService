package com.student.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

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

//	retry 
	@Bean
	public Retry retry() {
		RetryConfig retryConfig = RetryConfig.custom().maxAttempts(3) // Number of retry attempts
				.waitDuration(java.time.Duration.ofSeconds(2)) // Time between retries
				.build();

		RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);
		return retryRegistry.retry("retry");
	}

	@Bean
	public RateLimiter rateLimiter() {
		RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom().limitForPeriod(10) // Number of allowed calls
																							// per period
				.limitRefreshPeriod(java.time.Duration.ofSeconds(10)) // Period duration
				.timeoutDuration(java.time.Duration.ofMillis(500)) // Timeout for acquiring a permit
				.build();

		RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);
		return rateLimiterRegistry.rateLimiter("rateLimiter");
	}

}
