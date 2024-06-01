package com.cc.lab07tooldatascope.mybatis.datascope.model;


import com.cc.lab07tooldatascope.mybatis.datascope.constant.DataScopeConstant;
import com.cc.lab07tooldatascope.mybatis.datascope.constant.DataScopeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据权限实体类
 *
 */
@Data
@NoArgsConstructor
public class DataScopeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 构造器创建
     */
    public DataScopeModel(Boolean searched) {
        this.searched = searched;
    }

    /**
     * 数据库ID
     */
    private String id;

    /**
     * 是否已查询
     */
    private Boolean searched = Boolean.FALSE;

    /**
     * 数据权限字段
     */
    private String scopeColumn = DataScopeConstant.DEFAULT_COLUMN;
    /**
     * 数据权限规则
     */
    private String scopeType = DataScopeEnum.ALL.getType();
    /**
     * 可见字段
     */
    private String scopeField;
    /**
     * 数据权限规则值
     */
    private String scopeValue;
}
