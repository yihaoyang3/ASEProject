package com.aseproject.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @classname LoginInterceptor
 * @description Intercepting map uploading request, checking user is logged in or not before do any customized operations. If not, redirecting to login page
 * @author Yicheng Lu
 * @date Dec 5th, 2021
 */
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
