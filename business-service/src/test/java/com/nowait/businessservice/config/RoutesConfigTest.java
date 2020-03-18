package com.nowait.businessservice.config;

import com.nowait.businessservice.service.BusinessHandler;
import com.nowait.businessservice.repository.BusinessRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = {RoutesConfig.class})
//@ContextConfiguration(classes = {RoutesConfig.class, BusinessRepository.class,BusinessHandler.class})

@EnableAutoConfiguration

public class RoutesConfigTest {
    @MockBean
    BusinessRepository businessRepository;

    @MockBean
    BusinessHandler businessHandler;

    @Autowired
    RoutesConfig routesConfig;

    WebTestClient client;

    @Autowired
    ApplicationContext context;
    @Before
//    public void setUp() throws Exception {
//        this.client = WebTestClient
//                .bindToApplicationContext(this.context)
//                .build();
//    }
    public void setUp() throws Exception {
//        this.client = WebTestClient
//                .bindToRouterFunction(this.routesConfig.getRoute())
//                .configureClient()
//                .build();
    }

    @Test
    public void getAllBusinesses() {
        client.get()
                .uri("/mkget")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(String.class)
                .isEqualTo("hellow hi");
    }
}