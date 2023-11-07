package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.pojo.DictConfigDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictConfigMapper {

    DictConfigDO selectById(@Param("id") Long id);

    void insert(DictConfigDO dictConfigDO);

    void updateById(DictConfigDO dictConfigDO);
}
