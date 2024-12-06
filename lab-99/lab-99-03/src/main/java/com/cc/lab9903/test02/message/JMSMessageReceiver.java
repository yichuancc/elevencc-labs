package com.cc.lab9903.test02.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


/**
 * @author cc
 */
@Slf4j
@Component
public class JMSMessageReceiver {
//    /**
//     * 新增人员
//     *
//     * @param data
//     * @throws Exception
//     */
//    @JmsListener(destination = "MDM_Practitioner_Create_Ylzkpt")
//    public void createUser(String data) throws Exception {
//        log.info("接收新增人员MDM_Practitioner_Create_Ylzkpt：" + data);
//        System.out.println("接收新增人员MDM_Practitioner_Create_Ylzkpt：" + data);
//    }

    /**
     * 更新人员
     *
     * @param data
     * @throws Exception
     */
    @JmsListener(destination = "MDM_Practitioner_Update_Ylzkpt")
    public void updateUser(String data) throws Exception {
        log.info("接收更新人员MDM_Practitioner_Create_Ylzkpt：" + data);
        System.out.println("接收更新人员MDM_Practitioner_Create_Ylzkpt：" + data);
    }

    /**
     * 新增科室
     *
     * @param data
     * @throws Exception
     */
    @JmsListener(destination = "MDM_Organization_Create_Ylzkpt")
    public void createDept(String data) throws Exception {
        log.info("接收新增科室MDM_Organization_Create_Ylzkpt：" + data);
        System.out.println("接收新增科室MDM_Organization_Create_Ylzkpt：" + data);
    }

    /**
     * 更新科室
     *
     * @param data
     * @throws Exception
     */
    @JmsListener(destination = "MDM_Organization_Update_Ylzkpt")
    public void updateDept(String data) throws Exception {
        log.info("接收更新科室MDM_Organization_Update_Ylzkpt：" + data);
        System.out.println("接收更新科室MDM_Organization_Update_Ylzkpt：" + data);
    }

}
