package com.wadeyuan.training.dao;

import com.wadeyuan.training.entity.User;

public interface UserDao {

    User getUserByName(String userName);
}
