package com.wadeyuan.training.service;

import com.wadeyuan.training.entity.User;
import com.wadeyuan.training.exception.ParameterException;
import com.wadeyuan.training.exception.ServiceException;

public interface UserService {

    User login(String userName, String password) throws ParameterException, ServiceException;
}
