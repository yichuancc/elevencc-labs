package com.central.generator.controller;

import com.central.generator.model.PageResult;
import com.central.generator.service.SysGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/generator" )
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    /**
     * 列表
     */
    @ResponseBody
    @GetMapping("/list" )
    public PageResult getTableList(@RequestParam Map<String, Object> params) {
        return sysGeneratorService.queryList(params);
    }

    @ResponseBody
    @GetMapping("/v1/list" )
    public Object getTableListV1(@RequestParam Map<String, Object> params) {
        return "business success!!";
    }

    /**
     * 生成代码FileUtil
     */
    @GetMapping("/code" )
    public void makeCode(String tables, HttpServletResponse response) throws IOException {
        byte[] data = sysGeneratorService.generatorCode(tables.split("," ));
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + "code.zip" );
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8" );
        IOUtils.write(data, response.getOutputStream());
    }
}
