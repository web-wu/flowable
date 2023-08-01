package com.tabwu.dynamicAddFeild;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @PROJECT_NAME: flowable_parent
 * @USER: tabwu
 * @DATE: 2023/8/1 10:11
 * @DESCRIPTION:
 */
@SpringBootApplication
@MapperScan("com.tabwu.dynamicAddFeild.mapper")
public class FeildApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeildApplication.class, args);
    }
}
