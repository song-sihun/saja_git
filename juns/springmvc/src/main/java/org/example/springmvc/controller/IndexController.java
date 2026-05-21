package org.example.springmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class IndexController {

    @GetMapping("/index")
    public String index(
            @RequestParam(name = "name", defaultValue = "Guest", required = false) String name,
            @RequestParam(name = "message", defaultValue = "Hello", required = false) String message,
            Model model
    ) {
        model.addAttribute("name", name);
        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/index3/{newsid}")
    public String index3(@PathVariable(name = "newsid") String id){
        log.info("newsid::{}", id);
        return "welcome";
    }

    @GetMapping("/index/{id}")
    public String index(@PathVariable(name = "id") String id, Model model) {
        log.info("id::{}", id);
        model.addAttribute("id", id);
        return "index";
    }

}
