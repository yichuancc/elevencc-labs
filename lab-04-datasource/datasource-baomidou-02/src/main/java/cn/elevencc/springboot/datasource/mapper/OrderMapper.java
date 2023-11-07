package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.constant.DBConstants;
import cn.elevencc.springboot.datasource.pojo.OrderDO;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {

    /**
     * 读操作，配置了 @DS(DBConstants.DATASOURCE_SLAVE) ，访问从库
     */
    @DS(DBConstants.DATASOURCE_SLAVE)
    OrderDO selectById(@Param("id") Integer id);

    /**
     * 写操作，配置了 @DS(DBConstants.DATASOURCE_MASTER) ，访问主库。
     */
    @DS(DBConstants.DATASOURCE_MASTER)
    int insert(OrderDO entity);

}
