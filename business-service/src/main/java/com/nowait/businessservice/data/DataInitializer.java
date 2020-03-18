package com.nowait.businessservice.data;

import com.nowait.businessservice.domain.Business;
import com.nowait.businessservice.repository.BusinessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final BusinessRepository businessRepository;

    public DataInitializer(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data init");

        businessRepository.deleteAll()
                .thenMany(
                        Flux.fromIterable(getBusinessesList())
                                .flatMap(b -> businessRepository.save(b))
                )
                .log()
                .subscribe(null, null,
                        () -> log.info("done creating sample businesses"));
    }

    private List<Business> getBusinessesList() {
        Business b1 = new Business("1", "IBM", "www.ibm.com", "New York", "USA", "23434");
        Business b2 = new Business("2", "Microsoft", "www.Microsoft.com", "Redmond", "USA", "23232");
        Business b3 = new Business("3", "Oracle", "www.Oracle.com", "Redwood City", "USA", "84455");
        List<Business> businesses = Arrays.asList(b1, b2, b3);
        return businesses;
    }
}
