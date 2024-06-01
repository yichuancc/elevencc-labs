package com.cc.lab07tooldatascope.module.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lab07tooldatascope.module.entity.SysRoleDataScope;
import com.cc.lab07tooldatascope.module.mapper.SysRoleDataScopeMapper;
import com.cc.lab07tooldatascope.module.service.SysRoleDataScopeService;
import com.cc.lab07tooldatascope.mybatis.datascope.model.DataScopeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysRoleDataScopeServiceImpl extends ServiceImpl<SysRoleDataScopeMapper, SysRoleDataScope> implements SysRoleDataScopeService {


    @Override
    public List<DataScopeModel> dataByMapper(String mapperId, String roleId) {
        return baseMapper.dataByMapper(mapperId, roleId);
    }

}
