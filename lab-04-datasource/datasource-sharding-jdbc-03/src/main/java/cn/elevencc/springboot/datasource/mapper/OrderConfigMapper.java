package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.pojo.OrderConfigDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderConfigMapper {

    OrderConfigDO selectById(@Param("id") Integer id);

}
