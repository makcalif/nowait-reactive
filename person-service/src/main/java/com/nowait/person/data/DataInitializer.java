package com.nowait.person.data;

import com.nowait.person.domain.Person;
import com.nowait.person.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final PersonRepository personRepository;

    public DataInitializer(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data init");

        personRepository.deleteAll()
                .thenMany(
                        Flux.fromIterable(getPersonList())
                                .flatMap(b -> personRepository.save(b))
                )
                .log()
                .subscribe(null, null,
                        () -> log.info("done creating sample Persons"));
    }

    private List<Person> getPersonList() {
        Person p1 = new Person("1", "1", "Mumtaz Khan", "123-434-3434");
        Person p2 = new Person("2", "2", "John Doe", "123-434-3434");
        Person p3 = new Person("3", "3", "Brian Reese", "123-434-3434");
        Person p4 = new Person("4", "4", "Edward Snowden", "123-434-3434");
        Person p5 = new Person("5", "1", "Donald Trump", "123-434-3434");
        List<Person> persons = Arrays.asList(p1, p2, p3, p4, p5);
        return persons;
    }
}
