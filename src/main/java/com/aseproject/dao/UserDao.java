package com.aseproject.dao;

import com.aseproject.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao
{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User checkLogin(User checkedUser)
    {
        User userInfoInDb = new User();
        String sqlStr = "select password, user_id,is_admin,user_account_name from user where user_name = ?";
        jdbcTemplate.query(sqlStr, new Object[]{checkedUser.getUserName()}, resultSet -> {
            userInfoInDb.setUserId(resultSet.getString("user_id"));
            userInfoInDb.setPassword(resultSet.getString("password"));
            userInfoInDb.setUserAccountName(resultSet.getString("user_account_name"));
        });
        if (!checkedUser.getPassword().equals(userInfoInDb.getPassword()))
        {
            return null;
        }
        return userInfoInDb;
    }
}
