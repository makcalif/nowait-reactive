package com.nowait.apigateway.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class GatewayConfig {
    @Bean
    WebClient client () {
        return WebClient.builder()
                .build();
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/businesses/**")
                        .filters(f -> f.rewritePath("/api/v1/businesses(?<remains>.*)", "/businesses${remains}")
                                .addRequestHeader("X-Business-Header", "business-header")
                                .hystrix(c -> c.setName("hystrix")
                                        .setFallbackUri("forward:/fallback/businesses")))
                        .uri("lb://BUSINESS-SERVICE/")
                        .id("business-service"))

                .route(r -> r.path("/api/v1/persons/**")
                        .filters(f -> f.rewritePath("/api/v1/persons(?<remains>.*)", "/persons${remains}")
                                .hystrix(c -> c.setName("hystrix")
                                        .setFallbackUri("forward:/fallback/persons")))
                        .uri("lb://PERSON-SERVICE/")
                        .id("person-service"))
                .build();
    }

    @Bean
    @Order(-1)
    RouteLocator getwayRoutesReactive(RouteLocatorBuilder locator) {
        return locator.routes()
        .route(r -> r.path("/reactive2/**")
                .filters(f -> f.rewritePath("/reactive2/(?<remains>.*)", "/${remains}")
                        .addRequestHeader("X-first-Header", "first-service-header")
                         )
                .uri("lb://BUSINESS-SERVICE/")
                .id("business-service"))
                .build();
//        return locator.routes()
//                .route("business-service-reactive", predicate -> {
//                    return predicate.path("/reactive2/**")
//                            .uri("lb://BUSINESS-SERVICE/");
//                })
//                .build();
    }

    @Bean
    RouterFunction<ServerResponse> routes(WebClient webClient) {
        return RouterFunctions.route(
                RequestPredicates.GET("/reactive"),
                (req -> {
                    Flux<Map> businesses = webClient.get()
                            .uri("http://localhost:8086/")
                            .retrieve()
                            .bodyToFlux(Map.class);

                    return ServerResponse.ok().body(businesses, Map.class);
                })
        );
    }

    @Bean
    RouterFunction<ServerResponse> routes2(WebClient webClient) {
        return RouterFunctions.route(
                RequestPredicates.GET("/reactive/2"),
                (req -> {
                    Flux<Map> businesses = webClient.get()
                            .uri("http://localhost:8086/2")
                            .retrieve()
                            .bodyToFlux(Map.class);

                    return ServerResponse.ok().body(businesses, Map.class);
                })
        );
    }

    @Bean
    RouterFunction<ServerResponse> mergingRoutes(WebClient webClient) {
        return RouterFunctions.route(
          RequestPredicates.GET("/business/{id}/persons"),
                (req -> {

                    Flux<Person> persons = webClient.get()
                            .uri("http://localhost:8084/persons/" + req.pathVariable("id"))
                            .retrieve()
                            .bodyToFlux(Person.class);
                    return ServerResponse.ok().body(persons, Person.class);

//                    Mono<Business> business = webClient.get()
//                    .uri("http://localhost:8084/persons/" + req.pathVariable("id"))
//                    .retrieve()
//                    .bodyToMono(Business.class)
//                            .flatMapMany( b -> {
//                                     webClient
//                                    .get()
//                                    .uri("http://localhost:8084/persons/" + b.getId())
//                                    .retrieve()
//                                    .bodyToFlux()
//                                }
//                            })

                    //return ServerResponse.ok().body(business, Business.class);
                })
        );
    }

    @Bean
    RouterFunction<ServerResponse> mergingRoutes2(WebClient webClient) {
        return RouterFunctions.route(
                RequestPredicates.GET("/business/{id}/persons2"),
                (req -> {
                    Mono<BusinessWithPersons> businessWithPersonsMono = webClient.get()
                            .uri("http://localhost:8084/persons/" + req.pathVariable("id"))
                            .retrieve()
                            .bodyToFlux(Person.class)
                            .collectList()
                            .flatMap( plist -> { 
                                return  webClient.get()
                                        .uri("http://localhost:8086/businesses/" + req.pathVariable("id"))
                                        .retrieve()
                                        .bodyToMono(Business.class)
                                        .flatMap(biz -> Mono.just(new BusinessWithPersons(biz, plist)));
                            });

                    return ServerResponse.ok().body(businessWithPersonsMono, BusinessWithPersons.class);

                })
        );
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class BusinessWithPersons {
    private Business Business;
    private List<Person> persons;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Business {
    String id;
    String name;
    String url;
    String city;
    String country;
    String zip;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Person {
    String id;
    String businessId;
    String name;
    String phone;
}