package com.aseproject.service;

import com.aseproject.customexception.UserNameExisted;
import com.aseproject.dao.RegisterDao;
import com.aseproject.util.IdBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService
{
    @Autowired
    private RegisterDao dao;

    public String registerNewUser(String userName, String userAccountName, String password) throws UserNameExisted
    {
        String userId = IdBuilder.generateId();
        synchronized (this)
        {
            if (dao.checkUserName(userName))
            {
                dao.registerNewUser(userId, userName, userAccountName, password);
            } else
            {
                throw new UserNameExisted(userName + " already existed.");
            }
        }
        return userId;
    }
}
