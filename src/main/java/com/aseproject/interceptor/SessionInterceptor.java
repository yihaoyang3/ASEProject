package com.aseproject.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @classname SessionInterceptor
 * @description Checking each front webpage has a session or not. If not, create session
 * @author Yihao Yang
 * @date Dec 5th, 2021
 */
public class SessionInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        HttpSession session = request.getSession(false);
        if (session == null)
        {
            System.out.println("No session, create one");
            session = request.getSession();
            session.setAttribute("isLoggedIn", false);
        }
        return true;
    }
}
