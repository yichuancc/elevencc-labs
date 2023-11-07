package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.pojo.OrderDO;
import cn.elevencc.springboot.datasource.constant.DBConstants;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * DS 注解: dynamic-datasource-spring-boot-starter 提供。
 * 可添加在 Service 或 Mapper 的类/接口上，或者方法上。在其 value 属性种，填写数据源的名字。
 */
@Repository
@DS(DBConstants.DATASOURCE_ORDERS)
public interface OrderMapper {

    OrderDO selectById(@Param("id") Integer id);

}
