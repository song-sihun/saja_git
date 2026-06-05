package org.diner.dinerreserve.controller;

import lombok.RequiredArgsConstructor;
import org.diner.dinerreserve.domain.RestaurantTable;
import org.diner.dinerreserve.service.RestaurantTableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/table")
public class RestaurantTableController {

    private final RestaurantTableService restaurantTableService;

    @GetMapping("/all")
    public String allTables(Model model) {
        Iterable<RestaurantTable> tables = restaurantTableService.findAll();
        model.addAttribute("tables", tables);
        return "/restaurants/restaurant-tables";
    }
}
