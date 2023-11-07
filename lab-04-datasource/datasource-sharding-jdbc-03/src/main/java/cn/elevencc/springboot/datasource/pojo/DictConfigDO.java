package cn.elevencc.springboot.datasource.pojo;

import lombok.Data;

/**
 * 字典配置 DO
 */
@Data
public class DictConfigDO {

    /**
     * 编号
     */
    private Long id;
    /**
     * 字典类型
     */
    private Integer dictType;

}
