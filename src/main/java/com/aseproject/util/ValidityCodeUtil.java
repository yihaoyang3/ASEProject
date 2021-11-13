package com.aseproject.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class ValidityCodeUtil
{
    public static boolean validating(HttpServletRequest request)
    {
        boolean validatingStatus;
        String validityCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String userInput = request.getParameter("validityCode");
        if (validityCode.equals(userInput))
        {
            validatingStatus = true;
        }else
        {
            validatingStatus = false;
        }
        return validatingStatus;
    }
}
