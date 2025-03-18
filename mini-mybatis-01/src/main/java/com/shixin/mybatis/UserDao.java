package com.shixin.mybatis;

public class UserDao {
    public String deleteUser(Long id) {
        return "delete user by id: " + id;
    }
}
