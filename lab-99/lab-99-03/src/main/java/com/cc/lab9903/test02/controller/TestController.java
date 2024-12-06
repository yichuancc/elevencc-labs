package com.cc.lab9903.test02.controller;

import org.springframework.jms.core.JmsOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author cc
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private JmsOperations jmsOperations;

    @RequestMapping("/create-user")
    public String createUser() {
        try {
            jmsOperations.convertAndSend("MDM_Practitioner_Create_Ylzkpt", "创建人员成功");
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return "发送创建人员成功";
    }

    @RequestMapping("/update-user")
    public String updateUser() {
        try {
            jmsOperations.convertAndSend("MDM_Practitioner_Update_Ylzkpt", "更新人员成功");
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return "发送更新人员成功";
    }

    @RequestMapping("/create-dept")
    public String createDept() {
        try {
            jmsOperations.convertAndSend("MDM_Organization_Create_Ylzkpt", "创建科室成功");
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return "发送创建科室成功";
    }

    @RequestMapping("/update-dept")
    public String updateDept() {
        try {
            jmsOperations.convertAndSend("MDM_Organization_Create_Ylzkpt", "更新科室成功");
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return "发送更新科室成功";
    }
}
