package com.tabwu.flowable_demo1.entity;

import lombok.Data;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/26 15:44
 * @DESCRIPTION:
 */
@Data
public class ApproveAndRejectedVo {
    private String id;
    private boolean approved;
    private String name;
}
