package com.nowait.businessservice.config;

import com.nowait.businessservice.service.BusinessHandler;
import com.nowait.businessservice.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RoutesConfig {
    @Bean
    public RouterFunction<ServerResponse> routes (
            //BusinessRepository businesses,
            BusinessHandler businessController	) {
        RouterFunction<ServerResponse> businessesRoutes =
                RouterFunctions.route(
                        RequestPredicates.accept(MediaType.APPLICATION_JSON)
                                .and(RequestPredicates.GET("/businesses")), businessController::all)
                        .andRoute(
                                RequestPredicates.accept(MediaType.APPLICATION_JSON)
                                        .and(RequestPredicates.GET("/businesses/stream")),
                                                               businessController::stream
                        )
                        .andRoute(RequestPredicates.GET("/businesses/{id}"), businessController::get)
                        .andRoute(RequestPredicates.POST("/businesses"), businessController::create);

        return businessesRoutes;
    }
}
