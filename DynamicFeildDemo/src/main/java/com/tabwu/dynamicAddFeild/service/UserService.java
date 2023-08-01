package com.tabwu.dynamicAddFeild.service;

import com.tabwu.dynamicAddFeild.entity.User;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/8/1 10:24
 * @DESCRIPTION:
 */
public interface UserService {

    int addUser(User user);

    int deleteUser(Integer id);

    User queryUserById(Integer id);
}
