package org.lion.restexam.controller;

import org.lion.restexam.config.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ErrorTestController {
    @GetMapping("/errtest")
    public ResponseEntity<String> test() {
        throw new RuntimeException("test");
    }

    @GetMapping("/productErrTest")
    public ResponseEntity<String> test2() {
        throw new ProductNotFoundException("Product Not Found Exception");
    }
}
