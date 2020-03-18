package com.nowait.person.service;

import com.nowait.person.domain.Person;
import com.nowait.person.repository.PersonRepository;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PersonHandler {
    private final PersonRepository personRepository;

    public PersonHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Mono<ServerResponse> stream(ServerRequest serverRequest) {
        String businessId = serverRequest.pathVariable("businessId");
        return personRepository.findAll(
                Example.of(new Person(null, businessId, null, null)))
                .collectList()
                .flatMap(f -> ok()
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(BodyInserters.fromValue(f)));

//        Mono<Person> mono = null;
//        ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_STREAM_JSON)
//                .body(ServerResponse.ok().body(mono));
        //return  null;
    }

    public Mono<List<Person>> fluxAll(ServerRequest serverRequest) {
        String businessId = serverRequest.pathVariable("businessId");
//        ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_STREAM_JSON)
//                .body(personRepository.findAll(
//                        Example.of(new Person(null, businessId, null, null)))
//                         ;

        Mono<List<Person>> fluxList = personRepository.findAll(
                Example.of(new Person(null, businessId, null, null)))
                .collectList();
        return fluxList;
            //return ServerResponse.ok().body(BodyInserters.fromObject(fluxList));

//        return personRepository.findAll(
//                Example.of(new Person(null, businessId, null, null)))
//                .collectList()
//                .flatMap(d -> {
//                   // Mono<List<Person>>.just(d)
//                })
//                .flatMap(f -> ServerResponse.ok().body(Mono.just(f));
////                .collectList()
////                .flatMap(f -> {
////                    return ServerResponse.ok().body(Mono.just(f));
////                });
    }

//    public Mono<Person> stream(ServerRequest serverRequest) {
//
//    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Person.class)
                .flatMap(person -> this.personRepository.save(person))
                .flatMap(savedPerson -> ServerResponse.created(URI.create("/" + savedPerson.getId())).build());
    }
}
