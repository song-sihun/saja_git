package org.diner.dinerreserve.controller;

import lombok.RequiredArgsConstructor;
import org.diner.dinerreserve.service.ReservationSlotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/slot")
public class ReservationSlotController {

    private final ReservationSlotService reservationSlotService;

    @GetMapping("/all")
    public String allSlots(Model model) {
        return "/reservation/slots";
    }


}
