package org.example.springmvc.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.springmvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class UserLoginController {
    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login_form";
    }

    @PostMapping("/login")
    public String login(
            @Valid @RequestParam("username") String username,
            @Valid @RequestParam("password") String password,
            HttpServletResponse response
    ){
        if(userService.login(username, password)){
            log.info("{}:{}", username, password);
            Cookie cookie = new Cookie("login", "true");
            Cookie user = new Cookie("username", username);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            user.setPath("/");
            user.setMaxAge(3600);

            response.addCookie(cookie);
            response.addCookie(user);

            return "redirect:/user-list";
        }
        return "redirect:/login";
    }

    @GetMapping("/user-list")
    public String userList(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        Map<String,String> users = new HashMap<>();
        log.info("cookies: {}", (Object) cookies);

        if(cookies != null){
            for(Cookie cookie : cookies){
                if (cookie.getName().equals("username")) {
                    log.info("cookie name: {}", cookie.getName());
                    log.info("cookie value: {}", cookie.getValue());

                    users.put(cookie.getName(),cookie.getValue());
                }
            }
        }

        model.addAttribute("users",users);

        return "user_list";
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if (cookie.getName().equals("login") || cookie.getName().equals("username")) {
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/login";
    }
    
}
