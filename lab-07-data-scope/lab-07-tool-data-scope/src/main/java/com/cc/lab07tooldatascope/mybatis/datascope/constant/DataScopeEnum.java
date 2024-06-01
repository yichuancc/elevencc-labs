package com.cc.lab07tooldatascope.mybatis.datascope.constant;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限类型
 *
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {
    /**
     * 全部数据
     */
    ALL("1", "全部"),

    /**
     * 本人可见
     */
    OWN("2", "本人可见"),

    /**
     * 所在机构可见
     */
    OWN_ORG("3", "所在机构可见"),

    /**
     * 所在机构及子级可见
     */
    OWN_ORG_CHILD("4", "所在机构及子级可见"),

    /**
     * 自定义机构
     */
    CUSTOM_ORG("5", "自定义机构"),
    /**
     * 自定义用户
     */
    CUSTOM_USER("6", "自定义用户"),
    /**
     * 自定义SQL
     */
    CUSTOM_SQL("7", "自定义SQL"),
    ;

    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String description;

    public static DataScopeEnum of(String dataScopeType) {
        if (dataScopeType == null) {
            return null;
        }
        DataScopeEnum[] values = DataScopeEnum.values();
        for (DataScopeEnum scopeTypeEnum : values) {
            if (StringUtils.equals(scopeTypeEnum.type, dataScopeType)) {
                return scopeTypeEnum;
            }
        }
        return null;
    }
}
