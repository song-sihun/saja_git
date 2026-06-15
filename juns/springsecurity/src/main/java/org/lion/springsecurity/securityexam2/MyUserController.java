package org.lion.springsecurity.securityexam2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class MyUserController {
    @GetMapping("/hello")
    public String hello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String message = null;
        if(authentication != null){
            message = authentication.getName();
        }
        return "hello " +  message;
    }

    @GetMapping("/abc")
    public String abc() {
        return "abc";
    }

    @GetMapping("/aaa")
    public String aaa() {
        return "aaa";
    }

    @GetMapping("/bbb")
    public String bbb() {
        return "bbb";
    }

    @GetMapping("/ccc")
    public String ccc(@AuthenticationPrincipal UserDetails user) {
        return "ccc 로그인 한 유저 " + user.getUsername();
    }


}
