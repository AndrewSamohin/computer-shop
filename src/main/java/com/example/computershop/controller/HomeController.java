package com.example.computershop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Computer Shop is running!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
