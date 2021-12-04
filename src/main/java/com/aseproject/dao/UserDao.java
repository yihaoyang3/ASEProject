package com.aseproject.dao;

import com.aseproject.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

//    @Autowired
//    public void init(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    /* Login */
    // check user's existence when login
    public UserInfo checkLogin(UserInfo checkedUser) {
        UserInfo userInfo = new UserInfo();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sqlStr = "select password, user_id, user_account_name from user where user_name = ?";
        jdbcTemplate.query(sqlStr, new Object[]{checkedUser.getUserName()}, resultSet -> {
            userInfo.setUserId(resultSet.getString("user_id"));
            userInfo.setPassword(resultSet.getString("password"));
            userInfo.setUserAccountName(resultSet.getString("user_account_name"));
        });
        if (!checkedUser.getPassword().equals(userInfo.getPassword()))
        {
            return null;
        }
        return userInfo;
    }

    /* Register */
    //  check whether user's name is occupied when registering
    public boolean checkUserName(String userName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "select count(user_name) from user where user_name = ?";
        int count = jdbcTemplate.queryForObject(sql,Integer.class,userName);
        return !(count >= 1);
    }

    // register
    public void registerNewUser(String userId, String userName, String userAccountName, String password) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "insert into user (user_id, user_account_name, user_name, password) values (?,?,?,?);";
        jdbcTemplate.update(sql, userId, userName, userAccountName, password);
    }

    /* Other operations */
    public void modifyPassword(String userId, String newPassword) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "update user set password = ? where user_id = ?";
        jdbcTemplate.update(sql, newPassword, userId);
    }

    public boolean checkOldPassword(String userId, String oldPassword) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "select count(password) from user where user_id = ? and password = ? and is_admin = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{userId, oldPassword,false}, Integer.class);
        return count == 1;
    }
}
