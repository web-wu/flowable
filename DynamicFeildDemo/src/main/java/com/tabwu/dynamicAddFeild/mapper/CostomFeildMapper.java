package com.tabwu.dynamicAddFeild.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/8/1 10:19
 * @DESCRIPTION:
 */
public interface CostomFeildMapper {

    @Update("alter table `costomfeilds` add ${feild} ${feildType} comment #{comment};")
    int addCostomFeild(HashMap<String, Object> feildsMap);

    @Update("alter table `costomfeilds` drop ${columnName}")
    int deleteCostomFeild(String columnName);

    @Update("ALTER TABLE ${tableName} MODIFY COLUMN ${columnName} ${columnType} COMMENT ${comment};")
    int modifyFeildComment(String tableName, String columnName, String columnType, String comment);

    @Insert("insert into `costomfeilds` (${nameSql}) values(${valueSql});")
    int addData(HashMap<String, Object> feildsMap);

    @Select("select * from `costomfeilds` where relationid = #{relationid};")
    HashMap<String, Object> queryDataByRelationid(String relationid);

    @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<Map> listTable();

    @Select("select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME=#{tableName} and COLUMN_NAME like #{prefix}")
    List<Map> listTableColumn(String tableName, String prefix);
}
