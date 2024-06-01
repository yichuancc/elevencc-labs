package com.cc.lab07tooldatascope.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lab07tooldatascope.module.entity.SysRoleDataScope;
import com.cc.lab07tooldatascope.mybatis.datascope.model.DataScopeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleDataScopeMapper extends BaseMapper<SysRoleDataScope> {

    /**
     * 根据mapperId获取数据权限配置
     *
     * @param mapperId
     * @param roleId
     * @return
     */
    List<DataScopeModel> dataByMapper(@Param("mapperId") String mapperId, @Param("roleId") String roleId);

}
