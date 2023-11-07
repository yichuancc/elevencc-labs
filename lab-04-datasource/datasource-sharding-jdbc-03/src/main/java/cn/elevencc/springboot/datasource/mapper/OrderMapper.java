package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.pojo.OrderDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {

    OrderDO selectById(@Param("id") Long id);

    List<OrderDO> selectListByUserId(@Param("userId") Integer userId);

    List<OrderDO> selectListAll();

    void insert(OrderDO order);

}
