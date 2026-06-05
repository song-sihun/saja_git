package org.diner.dinerreserve.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diner.dinerreserve.config.exception.CustomException;
import org.diner.dinerreserve.config.status.SessionConst;
import org.diner.dinerreserve.domain.Reservation;
import org.diner.dinerreserve.domain.ReservationSlot;
import org.diner.dinerreserve.domain.Restaurant;
import org.diner.dinerreserve.dto.reservation.MyReservationResponse;
import org.diner.dinerreserve.dto.reservation.ReservationRequest;
import org.diner.dinerreserve.dto.restaurant.RestaurantResponse;
import org.diner.dinerreserve.dto.user.SessionUser;
import org.diner.dinerreserve.service.ReservationService;
import org.diner.dinerreserve.service.ReservationSlotService;
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
@RequestMapping("reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final RestaurantService restaurantService;
    private final ReservationSlotService reservationSlotService;

    @GetMapping("/all")
    public String allReservations(Model model) {
        Iterable<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);
        return "reservation/all";
    }

    @GetMapping("/reserve/{restaurant_id}")
    public String reserveReservation(
            @PathVariable("restaurant_id") Long restaurantId,
            Model model,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        if(session.getAttribute(SessionConst.LOGIN_USER) == null) {
            return "redirect:/users/login";
        }

        RestaurantResponse restaurant = restaurantService.findById(restaurantId)
                .orElseThrow(() -> new CustomException("식당을 찾을 수 없습니다."));

        log.info(restaurant.getId().toString());

        Iterable<ReservationSlot> slots = reservationSlotService.findByRestaurantId(restaurantId);

        log.info(slots.toString());

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("reservationRequest", new ReservationRequest());
        model.addAttribute("slots", slots);

        return "reservation/reserve_form";

    }

    @PostMapping("/reserve/{restaurant_id}")
    public String reserve(
            @PathVariable("restaurant_id") Long restaurantId,
            @Valid @ModelAttribute ReservationRequest reservationRequest,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        if (bindingResult.hasErrors()) {
            RestaurantResponse restaurant = restaurantService.findById(restaurantId)
                    .orElseThrow(() -> new IllegalArgumentException("식당을 찾을 수 없습니다."));
            Iterable<ReservationSlot> slots = reservationSlotService.findByRestaurantId(restaurantId);

            model.addAttribute("restaurant", restaurant);
            model.addAttribute("reservationRequest", reservationRequest);
            model.addAttribute("slots", slots);

            return "reservation/reserve_form";
        }
        SessionUser user = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
        reservationService.reserve(user.getId(), restaurantId, reservationRequest);

        return  "redirect:/reservation/my";
    }


    @GetMapping("/my")
    public String myReservations(
            Model model,
            HttpSession session,
            @PageableDefault(size = 10, sort = "reservationDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
        if(sessionUser == null) {
            return "redirect:/users/login";
        }

        Page<MyReservationResponse> reservationPage = reservationService.findMyReservations(sessionUser.getId(), pageable);
        model.addAttribute("reservations", reservationPage.getContent());
        model.addAttribute("page", reservationPage);

        return "reservation/my";
    }

    @PostMapping("/cancel/{id}")
    public String cancelReservations(@PathVariable("id") Long reservationId, Model model, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
        if(sessionUser == null) {
            return "redirect:/users/login";
        }
        reservationService.cancel(reservationId, sessionUser.getId());
        return "redirect:/reservation/my";
    }
}
