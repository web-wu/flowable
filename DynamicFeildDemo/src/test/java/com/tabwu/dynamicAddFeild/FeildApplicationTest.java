package com.tabwu.dynamicAddFeild;

import com.tabwu.dynamicAddFeild.FeildApplication;
import com.tabwu.dynamicAddFeild.entity.User;
import com.tabwu.dynamicAddFeild.mapper.CostomFeildMapper;
import com.tabwu.dynamicAddFeild.mapper.UserMapper;
import com.tabwu.dynamicAddFeild.service.CostomfeildsService;
import com.tabwu.dynamicAddFeild.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/8/1 10:49
 * @DESCRIPTION:
 */
@SpringBootTest(classes = FeildApplication.class)
@RunWith(SpringRunner.class)
public class FeildApplicationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CostomfeildsService costomfeildsService;
    @Autowired
    private CostomFeildMapper costomFeildMapper;


    @Test
    public void addCostomFeild() {
        HashMap<String, Object> variable = new HashMap<>();
        variable.put("feild", "user_email");
        variable.put("feildType", "int");
        variable.put("comment", "邮箱");

        costomfeildsService.addCostomFeild(variable);
    }

    @Test
    public void deleteCostomFeild() {
        costomfeildsService.deleteCostomFeild("tets");
    }

    @Test
    public void modifyColumnComment() {
        costomFeildMapper.modifyFeildComment("costomfeilds", "tel", "varchar(11)", "'电话1'");
    }

    @Test
    public void addUser() {

        User user = new User();
        user.setUsername("tabwu");
        user.setAge(28);
        userMapper.insert(user);

        HashMap<String, Object> variable = new HashMap<>();
        variable.put("relationid", "test_" + user.getId());
        variable.put("tel", "12345678912");
        variable.put("updateTime", new Date());
        variable.put("score", 98);

        costomfeildsService.addData(variable);
    }

    @Test
    public void queryCostomFeild() {
//        user_-581390335
        User user = userMapper.selectById(-581390335);
        HashMap<String, Object> properties = costomfeildsService.queryDataByRelationid("user_-581390335");
        user.setCostomFeilds(properties);
        System.out.println("user = " + user);
    }

    @Test
    public void queryTableNameAndColumnName() {
        List<Map> listTables = costomFeildMapper.listTable();
        for (Map table : listTables) {
            System.out.println("currentDatabase = " + table.get("TABLE_SCHEMA") + "-----tableName = " + table.get("TABLE_NAME"));
            String tableName = (String) table.get("TABLE_NAME");
            List<Map> list = costomFeildMapper.listTableColumn(tableName, "user%");
            for (Map map : list) {
                System.out.println("currentDatabase = " + map.get("TABLE_SCHEMA") + "-----tableName = " + map.get("TABLE_NAME")
                        + "-----columnName = " + map.get("COLUMN_NAME") + "-----columnComment = " + map.get("COLUMN_COMMENT"));
            }
        }
    }
}
