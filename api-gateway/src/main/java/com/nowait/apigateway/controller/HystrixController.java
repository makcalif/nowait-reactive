package com.nowait.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class HystrixController {

    @GetMapping("/businesses")
    public String businessesServiceFallback(){
        return "This is a fallback for businesses service.";
    }

    @GetMapping("/persons")
    public String personServiceFallback(){
        return "Persons Server overloaded! Please try after some time.";
    }
}
