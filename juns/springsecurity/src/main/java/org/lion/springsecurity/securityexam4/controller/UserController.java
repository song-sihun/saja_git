package org.lion.springsecurity.securityexam4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.springsecurity.securityexam4.domain.User;
import org.lion.springsecurity.securityexam4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public String home(@AuthenticationPrincipal UserDetails user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("user", user);
            model.addAttribute("authorities", user.getAuthorities());
            model.addAttribute("isLogin", true);
        }
        else {
            model.addAttribute("username", "GUEST");
            model.addAttribute("user", null);
            model.addAttribute("authorities", Collections.emptyList());
            model.addAttribute("isLogin", false);
        }
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String email
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);

        User createdUser = userService.createUser(user);

        return "redirect:/";
    }


}
