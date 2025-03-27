package com.shixin.mybatis.mapper;

public class UserDao {
    public String deleteUser(Long id) {
        return "delete user by id: " + id;
    }
}
