package com.tabwu.dynamicAddFeild.service.impl;

import com.tabwu.dynamicAddFeild.mapper.CostomFeildMapper;
import com.tabwu.dynamicAddFeild.service.CostomfeildsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/8/1 10:28
 * @DESCRIPTION:
 */
@Service
public class CostomfeildsServiceImpl implements CostomfeildsService {

    @Autowired
    private CostomFeildMapper costomFeildMapper;

    @Override
    public int addCostomFeild(HashMap<String, Object> feildsMap) {
        return costomFeildMapper.addCostomFeild(feildsMap);
    }

    @Override
    public int deleteCostomFeild(String colunmName) {
        return costomFeildMapper.deleteCostomFeild(colunmName);
    }

    @Override
    public int addData(HashMap<String, Object> feildsMap) {
        Set<String> keySet = feildsMap.keySet();
        String nameSql = "";
        String valueSql = "";
        for (String key : keySet) {
            nameSql += key + ",";
            valueSql += "#{" + key + "},";
        }
        String nameSqlSub = nameSql.substring(0, nameSql.length() - 1);
        String valueSqlSub = valueSql.substring(0, valueSql.length() - 1);
        feildsMap.put("nameSql", nameSqlSub);
        feildsMap.put("valueSql", valueSqlSub);
        return costomFeildMapper.addData(feildsMap);
    }

    @Override
    public HashMap<String, Object> queryDataByRelationid(String relationid) {
        return costomFeildMapper.queryDataByRelationid(relationid);
    }
}
