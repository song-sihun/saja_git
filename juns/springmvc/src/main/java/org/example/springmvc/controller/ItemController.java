package org.example.springmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springmvc.domain.Item;
import org.example.springmvc.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    private static final List<Item> itemList = new ArrayList<>();
    private static long sequence = 0;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all-list")
    public String allList(Model model) {
        model.addAttribute("items", itemList);
        return "all_list";
    }

    @GetMapping("/register")
    public String register(){
        return "register_form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Item item, Model model){
        item.setId(++sequence);
        itemList.add(item);
        log.info("register:: {}", item);
        return "redirect:/item/all-list";
    }

}
