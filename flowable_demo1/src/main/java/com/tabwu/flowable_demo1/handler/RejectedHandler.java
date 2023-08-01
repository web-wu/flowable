package com.tabwu.flowable_demo1.handler;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/26 16:00
 * @DESCRIPTION:
 */
@Slf4j
public class RejectedHandler implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("任务被拒绝，发送拒绝消息。" + delegateExecution);
        log.error("任务被拒绝，发送拒绝消息：{}", delegateExecution);
    }
}
