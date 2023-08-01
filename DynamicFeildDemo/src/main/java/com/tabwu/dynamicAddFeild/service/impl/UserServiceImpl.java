package com.tabwu.dynamicAddFeild.service.impl;

import com.tabwu.dynamicAddFeild.entity.User;
import com.tabwu.dynamicAddFeild.mapper.UserMapper;
import com.tabwu.dynamicAddFeild.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/8/1 10:26
 * @DESCRIPTION:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(Integer id) {
        return 0;
    }

    @Override
    public User queryUserById(Integer id) {
        return null;
    }
}
