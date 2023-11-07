package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.pojo.UserDO;
import cn.elevencc.springboot.datasource.constant.DBConstants;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@DS(DBConstants.DATASOURCE_USERS)
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);

}
