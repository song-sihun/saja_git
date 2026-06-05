package org.example.friendapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.friendapp.domain.Friend;
import org.example.friendapp.repository.FriendRepository;
import org.example.friendapp.service.FriendService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/add")
    public String add() {
        return "friend/add_from";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Friend friend) {
        Friend friend1 = friendService.saveFriend(friend);
        log.info("friend1={}", friend1);
        return "redirect:/friend/list";
    }

    @GetMapping("/list")
    public String list(
            @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<Friend> friendPage = friendService.getFriends(pageable);
        model.addAttribute("friends", friendPage.getContent());
        model.addAttribute("page", friendPage);
        return "friend/list";
    }

    @GetMapping("/list/{id}")
    public String listDetail(@PathVariable Long id) {
        return "friend/detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Friend friend = friendService.getFriendById(id);
        model.addAttribute("friend", friend);
        return "friend/update_form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Friend friend) {
        friendService.updateFriend(id, friend);
        return "redirect:/friend/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        friendService.deleteFriend(id);
        return "redirect:/friend/list";
    }


}
