package org.example.springmvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.springmvc.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class HelloController {
    private final HelloService helloService;


    public HelloController(HelloService helloService){
        this.helloService = helloService;
    }

    @GetMapping("/")
    public String home(HttpServletRequest request){
        String name = request.getParameter("name");
        request.setAttribute("name", name);
        request.setAttribute("message","Welcome to Spring MVC!");
        request.setAttribute("timestamp", LocalDateTime.now());
        return "home";
    }

    @GetMapping("/home")
    public String home(@RequestParam(value = "name", defaultValue = "Guest") String name, Model model){
        model.addAttribute("name", name);
        model.addAttribute("message","Hello Spring MVC!");
        model.addAttribute("timestamp", LocalDateTime.now());
        log.info("name : {}", name);
        return "home";
    }


    @GetMapping("/hi")
    public String hello(){
        return "welcome";
    }

    @GetMapping("/bye")
    public String bye(){
        return "bye";
    }

    @GetMapping("/rest")
    @ResponseBody
    public String rest(){
        return "Hello rest";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(defaultValue = "손") String name, Model model){
        String greeting = "안녕하세요 " + name + "님";
        model.addAttribute("greeting", greeting);
        return "greeting";
    }

}
