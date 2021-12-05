package com.aseproject.service;

import com.aseproject.customexception.UserNameExisted;
import com.aseproject.dao.UserDao;
import com.aseproject.util.IdBuilder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService{

    public String registerNewUser(String userEmail, String accountName, String password) throws UserNameExisted {
        UserDao userDao = new UserDao();
        String userId = IdBuilder.generateId();
        synchronized (this) {
            if (userDao.checkUserExist(userEmail)) {
                userDao.registerNewUser(userId, userEmail, accountName, password);
            } else {
                throw new UserNameExisted(userEmail + " already existed.");
            }
        }
        return userId;
    }
}
