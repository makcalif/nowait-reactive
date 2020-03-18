package com.nowait.person.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RouterHandlers {
    public <T extends ServerResponse> Mono<ServerResponse> getAll(ServerRequest serverRequest) {

        return ServerResponse.ok().body(
                                BodyInserters.fromPublisher(Mono.just("mk get working"), String.class));
    }
}
