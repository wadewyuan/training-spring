package com.wadeyuan.training.dao;

import com.wadeyuan.training.entity.User;

public interface UserDao {

    public User getUserByName(String userName);
}
