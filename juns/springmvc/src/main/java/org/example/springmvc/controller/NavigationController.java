package org.example.springmvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class NavigationController {

    @GetMapping("/internal")
    public String internal(HttpServletRequest request) {
        request.setAttribute("data", "Test");
        return "forward:/destination";
    }

    @GetMapping("/destination")
    public String destination(HttpServletRequest request, Model model) {
        String data = (String) request.getAttribute("data");
        log.info("data: {}", data);
        model.addAttribute("data", data);
        return "welcome";
    }

    @GetMapping("?process")
    public String process(RedirectAttributes redirectAttributes, Model model) {
        model.addAttribute("message", "Success");
        redirectAttributes.addFlashAttribute("message", "Success");
        redirectAttributes.addAttribute("id", 123);
        return "redirect:/result?id={id}";
    }

    @GetMapping("/result")
    public String result(@RequestParam("id") int id, Model model) {
        log.info("id: {}", id);
        model.addAttribute("id", "id");

        return "result";
    }
}
