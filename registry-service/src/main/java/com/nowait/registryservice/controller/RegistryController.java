package com.nowait.registryservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistryController {

    @GetMapping(value = "/name")
    public String getName() {
        return "this is registry service";
    }
}
