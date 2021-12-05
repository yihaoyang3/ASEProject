package com.aseproject.dao;

import com.aseproject.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Database operations related to users
 * @author Jitong Yang
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    public void init(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    /**
     * Checking user's existence when login
     * @param checkedUser User DTO
     * @return User DTO
     */
    public UserInfo checkLogin(UserInfo checkedUser) {
        UserInfo userInfo = new UserInfo();
        String sql = "select * from user where account_name = ?";
        jdbcTemplate.query(sql, new Object[]{checkedUser.getAccountName()}, resultSet -> {
            userInfo.setUserId(resultSet.getString("user_id"));
            userInfo.setPassword(resultSet.getString("password"));
            userInfo.setAccountName(resultSet.getString("account_name"));
            userInfo.setEmail(resultSet.getString(("email")));
        });
        if (!checkedUser.getPassword().equals(userInfo.getPassword())) {
            return null;
        }
        return userInfo;
    }

    /**
     * Checking whether user's name is occupied when registering
     * @param userEmail User email string
     * @return Boolean value depending on user name exist or not
     */
    public boolean checkUserExist(String userEmail) {
        String sql = "select count(email) from user where email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userEmail);
        return !(count >= 1);
    }

    /**
     * Inserting user information into database
     * @param userId User unique id string
     * @param accountName User customized account name
     * @param userEmail User email string
     * @param password User password
     */
    public void registerNewUser(String userId, String accountName, String userEmail, String password) {
        String sql = "insert into user (user_id, account_name, email, password) values (?,?,?,?);";
        jdbcTemplate.update(sql, userId, accountName, userEmail, password);
    }

    /**
     * Changing password
     * @param userId User unique id string
     * @param newPassword New password
     */
    public void modifyPassword(String userId, String newPassword) {
        String sql = "update user set password = ? where user_id = ?";
        jdbcTemplate.update(sql, newPassword, userId);
    }

    /**
     * Checking user password is correct or not. Before registering or changing password operations
     * @param userId User unique id string
     * @param oldPassword Old Password
     * @return Boolean value depending on password is correct or not
     */
    public boolean checkOldPassword(String userId, String oldPassword) {
        String sql = "select count(password) from user where user_id = ? and password = ? and is_admin = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{userId, oldPassword,false}, Integer.class);
        return count == 1;
    }
}
