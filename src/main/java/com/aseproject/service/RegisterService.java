package com.aseproject.service;

import com.aseproject.customexception.UserNameExisted;
import com.aseproject.dao.UserDao;
import com.aseproject.util.IdBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handle the sign-up logic.
 * @author Yihao Yang, Yicheng Lu
 */
@Service
public class RegisterService{

    @Autowired
    private UserDao userDao;

    /**
     *
     * @param userEmail Email address of the user
     * @param accountName Name typed by the user
     * @param password User's password
     * @return User id
     * @throws UserNameExisted If the user's email existed, it throws an exception
     */
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
