package org.lion.springsecurity.securityexam2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class MyHomeController {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/abc")
    public String abc() {
        return "abc";
    }






}
