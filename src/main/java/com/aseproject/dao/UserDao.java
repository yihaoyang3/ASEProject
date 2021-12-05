package com.aseproject.dao;

import com.aseproject.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    public void init(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    /* Login */
    // check user's existence when login
    public UserInfo checkLogin(UserInfo checkedUser) {
        UserInfo userInfo = new UserInfo();
        String sql = "select user_id, account_name, password from user where email = ?";
        jdbcTemplate.query(sql, new Object[]{checkedUser.getEmail()}, resultSet -> {
            userInfo.setUserId(resultSet.getString("user_id"));
            userInfo.setPassword(resultSet.getString("password"));
            userInfo.setAccountName(resultSet.getString("account_name"));

        });
        if (!checkedUser.getPassword().equals(userInfo.getPassword())) {
            return null;
        }
        return userInfo;
    }

    /* Register */
    //  check whether user's name is occupied when registering
    public boolean checkUserExist(String userEmail) {
        String sql = "select count(email) from user where email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userEmail);
        return !(count >= 1);
    }

    // register
    public void registerNewUser(String userId, String accountName, String userEmail, String password) {
        String sql = "insert into user (user_id, account_name, email, password) values (?,?,?,?);";
        jdbcTemplate.update(sql, userId, accountName, userEmail, password);
    }

    /* Other operations */
    public void modifyPassword(String userId, String newPassword) {
        String sql = "update user set password = ? where user_id = ?";
        jdbcTemplate.update(sql, newPassword, userId);
    }

    public boolean checkOldPassword(String userId, String oldPassword) {
        String sql = "select count(password) from user where user_id = ? and password = ? and is_admin = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{userId, oldPassword,false}, Integer.class);
        return count == 1;
    }
}
