package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.pojo.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);

}
