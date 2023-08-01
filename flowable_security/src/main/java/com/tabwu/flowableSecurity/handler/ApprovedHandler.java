package com.tabwu.flowableSecurity.handler;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/26 15:58
 * @DESCRIPTION:
 */
@Slf4j
public class ApprovedHandler implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("任务通过： " + delegateExecution);
    }
}
