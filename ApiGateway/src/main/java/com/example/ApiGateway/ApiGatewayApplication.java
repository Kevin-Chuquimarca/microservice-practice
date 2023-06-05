package com.example.ApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("demo1", r -> r.path("/ca/**")
						.uri("lb://SERVICE-COMPANY-A"))
				.route("demo2", r -> r.path("/cb/**")
						.uri("lb://SERVICE-COMPANY-B"))
				.build();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
