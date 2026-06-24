package com.example.demo;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse<String>> hello() {
        return ResponseUtil.ok("Hello from Spring Boot!");
    }

    @GetMapping("/greet")
    public ResponseEntity<ApiResponse<String>> greet(@RequestParam String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Parameter 'name' must not be blank");
        }
        return ResponseUtil.ok("Hello, " + name + "!");
    }

    @GetMapping("/greet/{id}")
    public ResponseEntity<ApiResponse<String>> greetById(@PathVariable long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }
        return ResponseUtil.ok("Hello, user #" + id + "!");
    }
}
