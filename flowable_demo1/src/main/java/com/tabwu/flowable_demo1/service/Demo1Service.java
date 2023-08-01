package com.tabwu.flowable_demo1.service;

import com.tabwu.flowable_demo1.entity.ApproveAndRejectedVo;
import com.tabwu.flowable_demo1.entity.HistoryInfo;
import com.tabwu.flowable_demo1.entity.TaskVo;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/26 14:14
 * @DESCRIPTION:
 */
@Service
public class Demo1Service {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Transactional
    public String addTask(TaskVo taskVo) {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("name", taskVo.getName());
        variables.put("days", taskVo.getDays());
        variables.put("reason", taskVo.getReason());
        try {
            runtimeService.startProcessInstanceByKey("holidayRequest", taskVo.getName(), variables);
            return "添加任务成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "添加任务失败";
    }

    @Transactional
    public List<Map<String, Object>> displayTaskByCondition(String candidate) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(candidate).list();
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            variables.put("id", task.getId());
            list.add(variables);
        }
        return list;
    }

    @Transactional
    public void approveAndRejected(ApproveAndRejectedVo approveAndRejectedVo) {
        try {
            HashMap<String, Object> variable = new HashMap<>();
            variable.put("approved", approveAndRejectedVo.isApproved());
            variable.put("employee", approveAndRejectedVo.getName());
            Task task = taskService.createTaskQuery().taskId(approveAndRejectedVo.getId()).singleResult();
            taskService.complete(task.getId(), variable);
            if (approveAndRejectedVo.isApproved()) {
                Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                taskService.complete(t.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public List<HistoryInfo> displayTaskByEmpoyee(String empoyee) {
        ArrayList<HistoryInfo> historyInfos = new ArrayList<>();
        List<HistoricProcessInstance> historyList = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(empoyee).list();
        for (HistoricProcessInstance historicProcessInstance : historyList) {
            HistoryInfo historyInfo = new HistoryInfo();
            historyInfo.setStartTime(historicProcessInstance.getStartTime());
            historyInfo.setEndTime(historicProcessInstance.getEndTime());
            historyInfo.setStatus(3);
            List<HistoricVariableInstance> variableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();
            for (HistoricVariableInstance variableInstance : variableInstances) {
                String variableName = variableInstance.getVariableName();
                Object value = variableInstance.getValue();
                if ("name".equals(variableName)) {
                    historyInfo.setName((String) value);
                } else if ("days".equals(variableName)) {
                    historyInfo.setDays((Integer) value);
                } else if ("reason".equals(variableName)) {
                    historyInfo.setReason((String) value);
                } else if ("approved".equals(variableName)) {
                    boolean flag = (boolean) value;
                    if (flag) {
                        historyInfo.setStatus(1);
                    } else {
                        historyInfo.setStatus(2);
                    }
                }
            }
            historyInfos.add(historyInfo);
        }
        return historyInfos;
    }
}
