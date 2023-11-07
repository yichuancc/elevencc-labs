package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.pojo.OrderDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {

    OrderDO selectById(@Param("id") Integer id);

}
