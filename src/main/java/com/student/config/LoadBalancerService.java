package com.student.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;

//@LoadBalancerClient(value = "ADDRESS-SERVICE", configuration = MyCustomeLoadBalancerConfiguration.class)
//@LoadBalancerClient(value = "ADDRESS-SERVICE")

@Configuration
public class LoadBalancerService {

//	By default it is load balance by round robbin 
	@LoadBalanced
	@Bean
	public Feign.Builder feiBuilder() {
		return Feign.builder();
	}
}
