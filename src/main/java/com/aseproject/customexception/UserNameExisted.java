package com.aseproject.customexception;

/**
 * @classname UserNameExisted
 * @description Exception when detecting user's customized account name already existed while registering
 * @author Yihao Yang
 * @date Dec 5th, 2021
 */
public class UserNameExisted extends Exception
{
    private String message;

    public UserNameExisted()
    {
        super();
    }

    public UserNameExisted(String message)
    {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
