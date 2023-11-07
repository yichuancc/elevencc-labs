package cn.elevencc.springboot.datasource.pojo;

import lombok.Data;

/**
 * 订单 DO
 */
@Data
public class OrderDO {

    /**
     * 订单编号
     */
    private Integer id;
    /**
     * 用户编号
     */
    private Integer userId;

}
