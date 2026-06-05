package org.diner.dinerreserve.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.diner.dinerreserve.config.status.SessionConst;
import org.diner.dinerreserve.config.status.Role;
import org.diner.dinerreserve.dto.user.SessionUser;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect("/users/login");
            return false;
        }

        SessionUser loginUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);

        if (loginUser == null) {
            response.sendRedirect("/users/login");
            return false;
        }

        if (loginUser.getRole() != Role.ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }
}
