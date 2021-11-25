package com.aseproject.customexception;

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
