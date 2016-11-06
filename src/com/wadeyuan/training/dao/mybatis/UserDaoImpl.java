package com.wadeyuan.training.dao.mybatis;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.wadeyuan.training.dao.UserDao;
import com.wadeyuan.training.entity.User;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{

    private static final String USER_MAPPER = "com.wadeyuan.training.entity.mappers.UserMapper";

    /** SQL IDs **/
    private static final String SELECT_USER_BY_NAME = ".selectUserByName";
    /** End SQL IDs **/

    @Override
    public User getUserByName(String userName) {

        return getSqlSession().selectOne(USER_MAPPER + SELECT_USER_BY_NAME, userName);
    }
}
