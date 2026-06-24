package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Parameter 'name' must not be blank");
        }
        return "Hello, " + name + "!";
    }

    @GetMapping("/greet/{id}")
    public String greetById(@PathVariable long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }
        return "Hello, user #" + id + "!";
    }
}
