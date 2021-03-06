package com.aseproject.controller;

import com.aseproject.customexception.UserNameExisted;
import com.aseproject.dao.UserDao;
import com.aseproject.domain.UserInfo;
import com.aseproject.service.RegisterService;
import com.aseproject.util.ValidityCodeUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Handling request from login and register webpage
 * @author Yihao Yang
 */

@Controller
public class LoginController
{
    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Producer captchaProducer;

    /**
     * Setting default login url
     * @return Login url
     */
    @RequestMapping("/login")
    public String login()
    {
        return "/login";
    }

    /**
     * Setting default register url
     * @return Register url
     */
    @RequestMapping("/register")
    public String entrance()
    {
        return "/register";
    }

    /**
     * Generating validating code
     * @param request Http Servlet Request
     * @param response Http Servlet Response
     */
    @RequestMapping("/getValidityCode")
    public void generateCode(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            //Generate verification code
            String codeText = captchaProducer.createText();
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, codeText);
            //Send code to client
            BufferedImage bi = captchaProducer.createImage(codeText);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Receiving login request from webpage, check whether user exist or not
     * @param request Http Servlet Request
     * @param response Http Servlet Response
     * @param attributes Redirect Attributes
     * @param uncheckedUser User DTO
     * @return Redirecting to login page or home page according to $mark$
     */
    @RequestMapping("/checkin")
    public String login(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes, UserInfo uncheckedUser) {
        int mark = 0;
        try {
            if (ValidityCodeUtil.validating(request)) {
                UserInfo user = userDao.checkLogin(uncheckedUser);
                if (user == null) {
                    attributes.addFlashAttribute("successCode", 0);
                    attributes.addFlashAttribute("errorInfo", "Account/Password not matched");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("userId", user.getUserId());
                    session.setAttribute("accountName", user.getAccountName());
                    session.setAttribute("userEmail", user.getEmail());

                    Cookie[] cookies = new Cookie[]{new Cookie("UserEmail", user.getEmail()),
                            new Cookie("AccountName", user.getAccountName()), new Cookie("UserId", user.getUserId())};
                    for (Cookie cookie : cookies) {
                        response.addCookie(cookie);
                    }
                    mark = 1;
                }
            } else {
                attributes.addFlashAttribute("successCode", 0);
                attributes.addFlashAttribute("errorInfo", "Verification not matched");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mark == 0 ? "redirect:/login" : "redirect:/";
    }

    /**
     * Receiving register request from webpage and send to UserDao to inject user information into database
     * @param param Map structure used for storing user information
     * @param request Http Servlet Request
     * @param model Redirect Attributes
     * @return Redirecting to home or register page according to $success$
     */
    @RequestMapping("/register/newUser")
    public String registerNewUser(@RequestParam Map<String, Object> param, HttpServletRequest request, RedirectAttributes model)
    {
        boolean success = false;
        try
        {
            if (ValidityCodeUtil.validating(request)) {
                String userId = registerService.registerNewUser((String) param.get("userEmail"), (String) param.get("accountName"),
                        (String) param.get("userPassword"));
                HttpSession session = request.getSession();
                session.setAttribute("idLoggedIn", true);
                session.setAttribute("userId", userId);
                session.setAttribute("accountName", param.get("accountName"));
                session.setAttribute("userEmail", param.get("userEmail"));
                success = true;
            } else {
                model.addFlashAttribute("statusCode", 0);
                model.addFlashAttribute("statusInfo", "Verification code doesn't match");
            }
        } catch (UserNameExisted e) {
            model.addFlashAttribute("statusCode", 0);
            model.addFlashAttribute("statusInfo", "User name existed");
            e.printStackTrace();
        } catch (Exception e) {
            model.addFlashAttribute("statusCode", 0);
            model.addFlashAttribute("statusInfo", "Unknown error");
            e.printStackTrace();
        }
        return success ? "redirect:/" : "redirect:/register";
    }
}
