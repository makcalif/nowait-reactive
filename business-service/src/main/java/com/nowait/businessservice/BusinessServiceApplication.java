package com.nowait.businessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BusinessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessServiceApplication.class, args);
	}

//	@Bean
//	public RouterFunction<ServerResponse> routes (
//			BusinessRepository businesses,
//			BusinessHandler businessController	) {
//		RouterFunction<ServerResponse> businessesRoutes =
//				RouterFunctions.route(RequestPredicates.GET("/"), businessController::all)
//				.andRoute(
//						RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)
//						.and(RequestPredicates.GET("/")), businessController::stream
//				)
//				.andRoute(RequestPredicates.GET("/{id}"), businessController::get)
//				.andRoute(RequestPredicates.POST("/"), businessController::create)
//				.andRoute(RequestPredicates.GET("/mktest"), businessController::all)
//				.andRoute(RequestPredicates.path("/mkpath"), r -> ServerResponse.ok().body(fromObject("hellow hi")));
//
//		//return RouterFunctions.nest(RequestPredicates.path("/businesses"), businessesRoutes);
//		return businessesRoutes;
//	}
}
