package com.aseproject.interceptor;

import com.aseproject.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
        if (!isLoggedIn) {
            request.setAttribute("msg", "You must login before upload maps!");
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
