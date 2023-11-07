package cn.elevencc.springboot.datasource.pojo;

import lombok.Data;

/**
 * 订单配置 DO
 */
@Data
public class OrderConfigDO {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 支付超时时间
     * 单位：分钟
     */
    private Integer payTimeout;

}
