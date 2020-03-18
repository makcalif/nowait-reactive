package com.nowait.businessservice.service;

import com.nowait.businessservice.domain.Business;
import com.nowait.businessservice.repository.BusinessRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Component
public class BusinessHandler {
    private final BusinessRepository businessRepository;

    public BusinessHandler(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public Mono<ServerResponse> all(ServerRequest serverRequest) {
        return ServerResponse.ok().body(businessRepository.findAll(), Business.class);
    }

    public Mono<ServerResponse> stream(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Flux.interval(Duration.ofSeconds(5)).flatMap(
                 seconds -> this.businessRepository.findAll()), Business.class);

    }

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok().body(businessRepository.findById(id), Business.class);
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
       return
        serverRequest.bodyToMono(Business.class)
                .flatMap(business -> this.businessRepository.save(business))
                .flatMap(b -> ServerResponse.created(URI.create("/" + b.getId())).build());
    }
}
