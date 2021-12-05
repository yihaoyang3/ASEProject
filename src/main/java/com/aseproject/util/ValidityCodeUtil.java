package com.aseproject.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * A util class for validating verification code.
 * @author Yihao Yang
 */
public class ValidityCodeUtil
{
    /**
     *This method extract verification code from request and check if validation pass.
     * @param request HttpServletRequest
     * @return true if validation pass. false otherwise.
     */
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
