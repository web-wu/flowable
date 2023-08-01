package com.tabwu.dynamicAddFeild.service;

import java.util.HashMap;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/8/1 10:28
 * @DESCRIPTION:
 */
public interface CostomfeildsService {

    int addCostomFeild(HashMap<String, Object> feildsMap);

    int deleteCostomFeild(String colunmName);

    int addData(HashMap<String, Object> feildsMap);

    HashMap<String, Object> queryDataByRelationid(String relationid);
}
