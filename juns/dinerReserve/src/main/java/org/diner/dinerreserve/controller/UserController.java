package org.diner.dinerreserve.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diner.dinerreserve.config.status.SessionConst;
import org.diner.dinerreserve.dto.user.SessionUser;
import org.diner.dinerreserve.dto.user.LoginRequest;
import org.diner.dinerreserve.dto.user.UserCreateRequest;
import org.diner.dinerreserve.dto.user.UserUpdateRequest;
import org.diner.dinerreserve.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String joinFrom(Model model) {
        model.addAttribute("userCreateRequest", new UserCreateRequest());
        return "user/join";
    }

    @PostMapping("/join")
    public String join(
            @Valid @ModelAttribute UserCreateRequest userCreateRequest,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "user/join";
        }
        try {
            userService.join(userCreateRequest);

        } catch (IllegalArgumentException e){
            model.addAttribute("joinError", e.getMessage());
            return "redirect:/users/join";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "user/login";

    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginRequest loginRequest,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        if (bindingResult.hasErrors()) {
            return "user/login";
        }
        try {
            SessionUser sessionUser = userService.login(loginRequest);

            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(SessionConst.LOGIN_USER, sessionUser);

            return "redirect:/";

        } catch (IllegalArgumentException e) {
            model.addAttribute("loginError", e.getMessage());
            return "user/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(Model model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session.getAttribute(SessionConst.LOGIN_USER) == null) {
            return "redirect:/users/login";
        }

        SessionUser sessionUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
        if (sessionUser == null) {
            return "redirect:/users/login";
        }
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setName(sessionUser.getName());
        model.addAttribute("userUpdateRequest", userUpdateRequest);

        return "user/update";
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute UserUpdateRequest userUpdateRequest,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest
    ) {
        if (bindingResult.hasErrors()) {
            return "user/update";
        }
        HttpSession session = httpServletRequest.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }
        SessionUser sessionUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        SessionUser sessionUser1 = userService.update(userUpdateRequest, sessionUser);
        session.setAttribute(SessionConst.LOGIN_USER, sessionUser1);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        SessionUser sessionUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
        if (sessionUser == null) {
            return "redirect:/login";
        }

        userService.delete(sessionUser.getId());
        session.invalidate();
        return "redirect:/";
    }


}
