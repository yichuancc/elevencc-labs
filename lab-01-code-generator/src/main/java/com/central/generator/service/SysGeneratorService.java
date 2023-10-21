package com.central.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.central.generator.model.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SysGeneratorService extends IService {
    PageResult queryList(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    byte[] generatorCode(String[] tableNames);
}
