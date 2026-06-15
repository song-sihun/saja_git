package org.lion.springsecurity.securityexam2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/userinfo")
    public String userinfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "Anonymous";

        if (authentication != null) {
            username = authentication.getName();
        }

        // 3. HTML 템플릿에 "name"이라는 이름으로 유저 ID 전달
        model.addAttribute("name", username);

        return "userinfo" ;
    }
}
