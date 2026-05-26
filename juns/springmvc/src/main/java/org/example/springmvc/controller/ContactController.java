package org.example.springmvc.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.springmvc.dto.ContactRequest;
import org.example.springmvc.dto.ContactResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contact(){
        return "contact_form";
    }

    @PostMapping("/contact")
    public String contact(
            @Valid @ModelAttribute ContactRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){
        if (bindingResult.hasErrors()) {
            return "contact_form";
        }

        redirectAttributes.addFlashAttribute("username", request.username());
        redirectAttributes.addFlashAttribute("email", request.email());
        redirectAttributes.addFlashAttribute("message", request.message());
        redirectAttributes.addFlashAttribute("responseMessage", "Contact successful");

        return "redirect:/contact/success";
    }

    @GetMapping("/contact/success")
    public String contactSuccess(){
        return "contact_success";
    }
}
