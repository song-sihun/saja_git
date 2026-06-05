package org.diner.dinerreserve.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.diner.dinerreserve.config.status.SessionConst;
import org.diner.dinerreserve.domain.Restaurant;
import org.diner.dinerreserve.dto.restaurant.RestaurantResponse;
import org.diner.dinerreserve.dto.user.SessionUser;
import org.diner.dinerreserve.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    // 식당 조회
    private final RestaurantService restaurantService;

    @GetMapping("/all")
    public String all(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String keyword,
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            SessionUser loginUser =
                    (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
            model.addAttribute("loginUser", loginUser);
        } else {
            model.addAttribute("loginUser", null);
        }

        Page<RestaurantResponse> restaurants;

        if (keyword == null || keyword.isBlank()) {
            restaurants = restaurantService.findByActiveTrue(pageable);
        } else {
            restaurants = restaurantService.searchByNameOrAddress(keyword, pageable);
        }

        model.addAttribute("restaurants", restaurants.getContent());
        model.addAttribute("page", restaurants);
        model.addAttribute("keyword", keyword);

        return "/restaurants/restaurants";
    }



    @GetMapping("/{id}")
    public String restaurant(@PathVariable Long id, Model model) {
        RestaurantResponse restaurant = restaurantService.findById(id).orElseThrow();
        model.addAttribute("restaurant", restaurant);
        return "/restaurants/restaurant-details";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        restaurantService.deactivateRestaurant(id);
        return  "redirect:/restaurants/all";
    }

}