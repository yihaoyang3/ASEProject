package com.aseproject.service;

import com.aseproject.customexception.UserNameExisted;
import com.aseproject.dao.UserDao;
import com.aseproject.util.IdBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService{

    @Autowired
    private UserDao userDao;

    public String registerNewUser(String userEmail, String accountName, String password) throws UserNameExisted {
        String userId = IdBuilder.generateId();
        synchronized (this) {
            if (userDao.checkUserExist(userEmail)) {
                userDao.registerNewUser(userId, accountName, userEmail, password);
            } else {
                throw new UserNameExisted(userEmail + " already existed.");
            }
        }
        return userId;
    }
}
