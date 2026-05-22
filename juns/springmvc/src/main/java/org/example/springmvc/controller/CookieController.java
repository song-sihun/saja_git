package org.example.springmvc.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class CookieController {
    @GetMapping("/cookie-form")
    public String cookieForm(){
        return "cookie_form";
    }

    @GetMapping("/addcookie")
    public String addCookie(
            @RequestParam(value = "cookieName") String cookieName,
            @RequestParam(value = "cookieValue") String cookieValue,
            HttpServletResponse response
    ){
        log.info(cookieName);
        log.info(cookieValue);

        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);

        response.addCookie(cookie);

        return "redirect:/cookieView";
    }

    @GetMapping("/cookieView")
    public String cookieView(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        List<String> cookieList = new ArrayList<>();
        Map<String, String> cookieMap = new HashMap<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Map에 쿠키 이름(Key)과 쿠키 값(Value)을 저장
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }


//        if(cookies != null){
//            for (Cookie cookie : cookies) {
//                log.info(cookie.getName());
//                log.info(cookie.getValue());
//                cookieList.add(cookie.getName()+":::"+cookie.getValue());
//            }
//        }
////        model.addAttribute("cookieList", cookieList);

        model.addAttribute("cookieList", cookieMap);
        return "cookie_view";
    }
}
