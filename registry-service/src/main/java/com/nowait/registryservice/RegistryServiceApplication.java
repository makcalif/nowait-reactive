package com.nowait.registryservice;

import com.nowait.registryservice.config.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
//@EnableConfigurationProperties
public class RegistryServiceApplication implements CommandLineRunner {

	@Autowired
	Data data;

	public static void main(String[] args) {
		SpringApplication.run(RegistryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("config msg>>>>>>>>>>>:" + data.getMessage());
		System.out.println("config port >>>>>>>>>>>:" + data.getPort());
	}
}
