package com.nowait.person.repository;

import com.nowait.person.domain.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
}
