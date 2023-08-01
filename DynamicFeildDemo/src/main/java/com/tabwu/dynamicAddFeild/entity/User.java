package com.tabwu.dynamicAddFeild.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.HashMap;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/8/1 10:15
 * @DESCRIPTION:
 */
@Data
public class User {

    private Integer id;

    private String username;

    private Integer age;

    @TableField(exist = false)
    private HashMap<String, Object> costomFeilds = new HashMap<>();
}
