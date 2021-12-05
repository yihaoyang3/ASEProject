package com.aseproject.domain;

import java.io.Serializable;

public class UserInfo implements Serializable
{
    private String userId;
    private String accountName;
    private String userEmail;
    private String password;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getAccountName()
    {
        return this.accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getEmail()
    {
        return userEmail;
    }

    public void setEmail(String email)
    {
        this.userEmail = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
