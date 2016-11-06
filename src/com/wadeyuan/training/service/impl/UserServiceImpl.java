package com.wadeyuan.training.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.wadeyuan.training.dao.UserDao;
import com.wadeyuan.training.entity.User;
import com.wadeyuan.training.exception.ParameterException;
import com.wadeyuan.training.exception.ServiceException;
import com.wadeyuan.training.service.UserService;

public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String userName, String password) throws ParameterException, ServiceException{
        User user;
        ParameterException parameterException = new ParameterException();
        Map<String, String> errorFields = new HashMap<String, String>();
        parameterException.setErrorFields(errorFields);

        if(userName == null || userName.trim().isEmpty()) {
            errorFields.put("username", "User name is empty");
        }

        if(password == null || password.trim().isEmpty()) {
            errorFields.put("password", "Password is empty");
        }

        if(parameterException.hasErrorFields()) {
            throw parameterException;
        }

        user = userDao.getUserByName(userName);
        if(user == null) {
            throw new ServiceException("No such user");
        }
        if(!user.getPassword().equals(password)) {
            throw new ServiceException("Incorrect password");
        }
        return user;

    }
}
