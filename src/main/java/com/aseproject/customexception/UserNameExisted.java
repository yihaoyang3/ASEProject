package com.aseproject.customexception;

/**
 * Exception when detecting user's customized account name already existed while registering
 * @author Yihao Yang
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
