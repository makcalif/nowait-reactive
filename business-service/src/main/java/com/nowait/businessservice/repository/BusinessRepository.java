package com.nowait.businessservice.repository;

import com.nowait.businessservice.domain.Business;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface BusinessRepository extends ReactiveMongoRepository<Business, String> {

}
