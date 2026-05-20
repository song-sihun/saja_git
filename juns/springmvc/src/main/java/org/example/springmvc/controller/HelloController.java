package org.example.springmvc.controller;

import org.example.springmvc.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    private final HelloService helloService;


    public HelloController(HelloService helloService){
        this.helloService = helloService;
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
    public String greeting(@RequestParam(defaultValue = "Guest") String name, Model model){
        String greeting = helloService.sayHello(name);
        model.addAttribute("greeting",greeting);
        return "greeting";
    }

}
