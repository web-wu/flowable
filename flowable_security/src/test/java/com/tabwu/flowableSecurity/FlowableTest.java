package com.tabwu.flowableSecurity;

import com.tabwu.flowableSecurity.entity.User;
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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/31 10:04
 * @DESCRIPTION:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FlowableTest {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    /**
     * 手动部署流程
     */
    @Test
    public void deploymentProcess() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-innosen.bpmn20.xml")
                .name("请假流程部署")
                .deploy();
        System.out.println("deployment = " + deployment);
    }

    /**
     * 根据部署id删除部署的流程，第二个参数为true时，该部署流程存在运行的实例时也要删除
     */
    @Test
    public void deleteDeploymentProcess() {
        repositoryService.deleteDeployment("2a4e19a9-2f4e-11ee-b2c5-56fc7709360b", true);
    }

    /**
     * 开启一个实例任务
     */
    @Test
    public void startProcessInstance() {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("username", "luyao");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday-innosen", variables);
        System.out.println("processInstance = " + processInstance);
    }


    @Test
    public void submitHolidayByProcessInstance() {
        Task task = taskService.createTaskQuery().taskAssignee("luyao").singleResult();
        Map<String, Object> variables = task.getProcessVariables();
        variables.put("num", 1);
        variables.put("leader", "xz");
        variables.put("group", "sj");
        taskService.complete(task.getId(), variables);
    }

    @Test
    public void leaderApproveByProcessInstance() {
        Task task = taskService.createTaskQuery().taskAssignee("xz").singleResult();
        Map<String, Object> variables = task.getProcessVariables();
        variables.put("approve", true);
        variables.put("hr", "rj");
        taskService.complete(task.getId(), variables);
    }

    @Test
    public void groupApproveByProcessInstance() {
        Task task = taskService.createTaskQuery().taskAssignee("sj").singleResult();
        Map<String, Object> variables = task.getProcessVariables();
        variables.put("approve", false);
        variables.put("hr", "rj");
        taskService.complete(task.getId(), variables);
    }

    @Test
    public void hrApproveByProcessInstance() {
        Task task = taskService.createTaskQuery().taskAssignee("rj").singleResult();
        taskService.complete(task.getId());
    }


    /**
     * 查询hr审批过的任务，查询历史任务变量时，不能根据任务id查询，可以根据流程实例id和流程定义id查询
     */
    @Test
    public void hrApprovedTask() {
        List<HistoricTaskInstance> historyTasks = historyService.createHistoricTaskInstanceQuery().taskAssignee("rj").list();
        for (HistoricTaskInstance historyTask : historyTasks) {
            List<HistoricVariableInstance> variablesList = historyService.createHistoricVariableInstanceQuery().processInstanceId(historyTask.getProcessInstanceId()).list();
            for (HistoricVariableInstance variable : variablesList) {
                System.out.println("name = " + variable.getVariableName() + "------value = " + variable.getValue());
            }
        }
    }





    @Test
    public void dynamicAddFieldForObject() {
        User user = new User();
        Class<? extends User> userClass = user.getClass();
        try {
            Field ageField = userClass.getDeclaredField("age");
            ageField.setAccessible(true);
            ageField.set(user, 27);
            System.out.println("user = " + user);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
