package com.tabwu.flowable_demo1.entity;

import lombok.Data;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/26 14:17
 * @DESCRIPTION:
 */
@Data
public class TaskVo {
    private String name;
    private int days;
    private String reason;
}
