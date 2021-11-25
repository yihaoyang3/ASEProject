package com.aseproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterDao
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean checkUserName(String userName)
    {
        String sql = "select count(user_name) from market_user where user_name = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{userName}, Integer.class);
        return !(count >= 1);
    }

    public void registerNewUser(String userId, String userName, String userAccountName, String password)
    {
        String sql = "insert into user (user_id, user_account_name, user_name, password, is_admin) values (?,?,?,?,?);";
        jdbcTemplate.update(sql, userId, userName, userAccountName, password, false);
    }

    public void modifyPassword(String userId, String newPassword)
    {
        String sql = "update user set password = ? where user_id = ?";
        jdbcTemplate.update(sql, newPassword, userId);
    }

    public boolean checkOldPassword(String userId, String oldPassword)
    {
        String sql = "select count(password) from user where user_id = ? and password = ? and is_admin = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{userId, oldPassword,false}, Integer.class);
        return count == 1;
    }
}
