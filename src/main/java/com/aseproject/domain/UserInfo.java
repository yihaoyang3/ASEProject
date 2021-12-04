package com.aseproject.domain;

import java.io.Serializable;

public class UserInfo implements Serializable
{
    private String userId;
    private String userName;
    private String userAccountName;
    private String password;

    public String getUserAccountName()
    {
        return userAccountName;
    }

    public void setUserAccountName(String userAccountName)
    {
        this.userAccountName = userAccountName;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
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
