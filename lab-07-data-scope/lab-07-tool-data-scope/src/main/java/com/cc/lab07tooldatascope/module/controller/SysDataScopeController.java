package com.cc.lab07tooldatascope.module.controller;

import com.cc.lab07tooldatascope.module.entity.SysDataScope;
import com.cc.lab07tooldatascope.module.service.SysDataScopeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/data-scope")
public class SysDataScopeController {

    @Resource
    private SysDataScopeService sysDataScopeService;

    /**
     * 分页列表查询
     *
     * @return
     */
    @GetMapping(value = "/list")
    public List<SysDataScope> queryPage() {
        return sysDataScopeService.list();
    }

}
