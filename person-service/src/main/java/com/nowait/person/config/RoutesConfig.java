package com.nowait.person.config;

import com.nowait.person.service.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class RoutesConfig {
//    @Bean
//    public RouterFunction<ServerResponse> getRoute () {
//        RouterFunction<ServerResponse> routerFunction =
//                RouterFunctions.route(
//                        RequestPredicates.GET("/mkget"),
//                        r -> ServerResponse.ok().body(
//                                BodyInserters.fromPublisher(Mono.just("mk get working"), String.class)));
//        return routerFunction;
//    }

    @Bean
    RouterFunction<ServerResponse> routerFunction(PersonHandler personHandler) {

        return RouterFunctions.route(
                RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)
                        .and(RequestPredicates.GET("/persons/{businessId}")),
                personHandler::stream);
//                .andRoute(
//                        RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)
//                                .and(RequestPredicates.GET("/persons")),
//                        personHandler::stream);
//                );

    }
}
