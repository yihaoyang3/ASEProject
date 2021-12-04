package com.aseproject.service;

import com.aseproject.customexception.UserNameExisted;
import com.aseproject.dao.UserDao;
import com.aseproject.util.IdBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService{

    public String registerNewUser(String userName, String userAccountName, String password) throws UserNameExisted {
        UserDao userDao = new UserDao();
        String userId = IdBuilder.generateId();
        synchronized (this) {
            if (userDao.checkUserName(userName)) {
                userDao.registerNewUser(userId, userName, userAccountName, password);
            } else {
                throw new UserNameExisted(userName + " already existed.");
            }
        }
        return userId;
    }
}
