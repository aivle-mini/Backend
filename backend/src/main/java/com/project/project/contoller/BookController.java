package com.project.project.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
