package org.example.springmvc.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class SessionController {

    @ModelAttribute("visitCount")
    public int getVisitCount() {
        return 0;
    }

    @GetMapping("/sessionForm")
    public String sessionForm() {
        return "session_form";
    }

    @GetMapping("/addsession")
    public String addSession(@RequestParam("session") String sessionKey, @RequestParam("sessionValue")  String sessionValue, HttpSession session) {
        session.setAttribute(sessionKey, sessionValue);

        return "redirect:/sessionView";

    }

    @GetMapping("/sessionView")
    public String sessionView(HttpSession session, Model model) {
        Map<String, Object> sessions = new HashMap<>();

        Enumeration<String> keys = session.getAttributeNames();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            Object value = session.getAttribute(key);
            sessions.put(key, value);
        }
        log.info(String.valueOf(session));

        log.info("session values: {}", sessions);

        model.addAttribute("sessions", sessions);
        return "session_view";
    }

    @GetMapping("/sessionDel")
    public String sessionDel(HttpSession session, @RequestParam("sessionKey")  String sessionKey) {
        session.removeAttribute(sessionKey);
        return "redirect:/sessionView";
    }

    @GetMapping("/listView")
    public String listView(HttpSession session, Model model) {
//        로그인한 사용자에게만, welcome 페이지로 이동하고,
//        로그인하지 않은 사용자는 세션등록 페이지로 이동하게 코드를 만들어 볼까요?
        if("ok".equals(session.getAttribute("login"))) {
            return "welcome";
        }
        return "redirect:/sessionForm";

    }

    @GetMapping("/visit")
    public String visitView(HttpSession session, Model model) {
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 0;
        }

        visitCount = visitCount + 1;
        session.setAttribute("visitCount", visitCount);
        model.addAttribute("visitCount", visitCount);
        return "visit_view";
    }
}
