package com.tabwu.flowable_demo1;

import com.tabwu.flowable_demo1.entity.ApproveAndRejectedVo;
import com.tabwu.flowable_demo1.entity.HistoryInfo;
import com.tabwu.flowable_demo1.service.Demo1Service;
import org.flowable.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/7/26 14:47
 * @DESCRIPTION:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FlowableTest {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private Demo1Service demo1Service;


    @Test
    public void addTask() {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("name", "tabwu");
        variables.put("days", 1);
        variables.put("reason", "休息一下");
        try {
            runtimeService.startProcessInstanceByKey("holidayRequest", "tabwu", variables);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void displayTaskByCandidate() {
        List<Map<String, Object>> mapList = demo1Service.displayTaskByCondition("managers");
        for (Map<String, Object> map : mapList) {
            System.out.println("id = " + map.get("id") + "-----name = " + map.get("name") + "------days = " + map.get("days") + "----reason = " + map.get("reason"));
        }
    }


    @Test
    public void approveAndRejected() {
        ApproveAndRejectedVo approveAndRejectedVo = new ApproveAndRejectedVo();
        approveAndRejectedVo.setId("79373b90-2b8d-11ee-97e0-56fc7709360b");
        approveAndRejectedVo.setApproved(false);
        approveAndRejectedVo.setName("tabwu");
        demo1Service.approveAndRejected(approveAndRejectedVo);
    }

    @Test
    public void displayTaskByemployee() {
        List<HistoryInfo> historyInfos = demo1Service.displayTaskByEmpoyee("tabwu");

        for (HistoryInfo historyInfo : historyInfos) {
            System.out.println("name = " + historyInfo.getName() + "------days = " + historyInfo.getDays() + "----reason = "
                    + historyInfo.getReason() + "-----status = " + historyInfo.getStatus() + "-----startTime = " + historyInfo.getStartTime() + "-----endTime = " + historyInfo.getEndTime());
        }
    }
}
