package com.example.demo;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse<String>> hello() {
        return ResponseUtil.ok("Hello from Spring Boot!");
    }

    // add a new endpoint that returns a greeting message
    @GetMapping("/greet")
    public String greet() {
        return "Greetings from Spring Boot!";
    }
}
