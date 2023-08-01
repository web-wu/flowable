package com.tabwu.flowable_demo1.entity;

import lombok.Data;

import java.util.Date;


/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/26 17:09
 * @DESCRIPTION:
 */
@Data
public class HistoryInfo {
    private String name;
    private int days;
    private String reason;
    private int status;
    private Date startTime;
    private Date endTime;
}
