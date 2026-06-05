package org.diner.dinerreserve.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diner.dinerreserve.config.status.RestaurantCategory;
import org.diner.dinerreserve.domain.Restaurant;
import org.diner.dinerreserve.dto.restaurant.RestaurantCreateRequest;
import org.diner.dinerreserve.dto.restaurant.RestaurantResponse;
import org.diner.dinerreserve.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
    // 식당 등록 폼
    // 식당 등록
    // 식당 수정 폼
    // 식당 수정
    // 식당 비활성화
    private final RestaurantService restaurantService;

    @GetMapping
    public String restaurants(
            @RequestParam(required = false) String keyword,
            Model model,
            @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<RestaurantResponse> restaurants;

        if (keyword == null || keyword.isBlank()) {
            restaurants = restaurantService.findAll(pageable);
        } else {
            restaurants = restaurantService.findByNameContaining(keyword, pageable);
        }


        model.addAttribute("restaurants", restaurants);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", pageable);

        return "restaurants/restaurants";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("restaurantCreateRequest", new RestaurantCreateRequest());
        model.addAttribute("categories", RestaurantCategory.values());
        return "restaurants/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute RestaurantCreateRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", RestaurantCategory.values());
            return "restaurants/register";
        }
        restaurantService.register(request);
        return "redirect:/restaurants/all";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/users/login";
        }

        RestaurantResponse restaurantResponse = restaurantService.findById(id).orElseThrow();

        log.info("restaurant {}", restaurantResponse.getOpeningTime());
        log.info("restaurant {}", restaurantResponse.getClosingTime());

        model.addAttribute("restaurantCreateRequest", toUpdateRequest(restaurantResponse));
        model.addAttribute("restaurantId", id);
        model.addAttribute("categories", RestaurantCategory.values());

        return  "restaurants/update";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("restaurantCreateRequest") RestaurantCreateRequest updateRequest,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurantId", id);
            model.addAttribute("categories", RestaurantCategory.values());
            return "restaurants/update";
        }
        try{
            restaurantService.updateRestaurant(id, updateRequest);
        } catch (Exception e){
            model.addAttribute("categories", RestaurantCategory.values());
            return "restaurants/update";
        }
        return  "redirect:/restaurants/all";
    }

    private RestaurantCreateRequest toUpdateRequest(RestaurantResponse restaurantResponse) {
        return new RestaurantCreateRequest(
                restaurantResponse.getName(),
                restaurantResponse.getAddress(),
                normalizePhoneNumber(restaurantResponse.getPhoneNumber()),
                restaurantResponse.getDescription(),
                restaurantResponse.getCategory(),
                restaurantResponse.getOpeningTime(),
                restaurantResponse.getClosingTime()
        );
    }

    private String normalizePhoneNumber(String phoneNumber) {
        return phoneNumber == null ? "" : phoneNumber.replace("-", "");
    }

    @PostMapping("/disable/{id}")
    public String disable(@PathVariable Long id) {
        restaurantService.deactivateRestaurant(id);
        return   "redirect:/restaurants/all";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return   "redirect:/restaurants/all";
    }
}
