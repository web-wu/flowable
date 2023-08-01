package com.tabwu.flowableSecurity;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/28 13:16
 * @DESCRIPTION:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FlowableSecurity {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Test
    public void deployProcess() {
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("holiday12.bpmn20.xml").deploy();
        System.out.println("deploy = " + deploy);
    }

    @Test
    public void deleteDeployProcess() {
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : deployments) {
            System.out.println("deployment_id = " + deployment.getId() + "----deployment_key = " + deployment.getKey());
            /**
             * 第一个参数：部署的流程id，
             * 第二个参数：该流程有未完成任务时是否删除
             */
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }


    @Test
    public void startProcessTask() {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("approveUser", "tabwu");
        variables.put("name", "luyao");
        variables.put("days", 7);
        variables.put("reason", "三生三世");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday12", "luyao", variables);
        System.out.println("processInstance = " + processInstance);
    }

    @Test
    public void displayTaskBycandite() {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("tabwu").list();
        for (Task task : taskList) {
            Map<String, Object> variable = taskService.getVariables(task.getId());
            variable.put("id", task.getId());
            arrayList.add(variable);
        }

        for (Map<String, Object> map : arrayList) {
            System.out.println("id = " + map.get("id") + "---name = " + map.get("name") + "---days = " + map.get("days") + "---reason = " + map.get("reason"));
        }
    }


    @Test
    public void approveTask() {
        Task task = taskService.createTaskQuery().taskId("48ba4acb-2d17-11ee-9bdc-56fc7709360b").singleResult();
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("approve", true);
        variables.put("employee", "luyao");
        taskService.complete(task.getId(), variables);
    }

    @Test
    public void approvedTaskDisplay() {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
        List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().taskAssignee("tabwu").finished().finished().list();

        for (HistoricTaskInstance task : taskList) {
            List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().taskId(task.getId()).list();
            HistoricVariableInstance historicVariableInstance = list.get(0);
            //            historyService.createHistoricVariableInstanceQuery().taskId(task.getId());
//            variable.put("id", task.getId());
//            arrayList.add(variable);
        }

        for (Map<String, Object> map : arrayList) {
            System.out.println("id = " + map.get("id") + "---name = " + map.get("name") + "---days = " + map.get("days") + "---reason = " + map.get("reason"));
        }
    }
}
