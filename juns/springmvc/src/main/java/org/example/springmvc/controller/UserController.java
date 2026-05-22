package org.example.springmvc.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.springmvc.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping("/join")
    public String join(Model model){
        //회원가입 폼을 응답.
        model.addAttribute("user", new User());

        return "user_join_form";
    }
    @PostMapping("/join")
    public String join2(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){

//        입력된  값을 검증!!
        if(bindingResult.hasErrors()){
            return "user_join_form";
        }

//        회원가입로직 실행!!!
//        이름, 이메일, 패스워드 값을 얻어와서 log로 출력해보세요.

        log.info("name:{}",user.getName());
        log.info("password:{}",user.getPassword());
        log.info("email:{}",user.getEmail());
        return "redirect:/hi";
    }

    @GetMapping("/all-users")
    public String allUsersPage(Model model) {
        List<User> userList = Arrays.asList(
                new User("jun1", "1234", "test1@email.com"),
                new User("jun2", "1234", "test2@email.com"),
                new User("jun3", "1234", "test3@email.com"),
                new User("jun4", "1234", "test4@email.com")

        );
        model.addAttribute("users", userList);
        return "user_list";
    }
}
