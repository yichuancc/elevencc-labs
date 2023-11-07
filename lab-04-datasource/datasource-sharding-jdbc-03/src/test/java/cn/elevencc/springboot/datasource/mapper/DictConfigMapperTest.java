package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.Application;
import cn.elevencc.springboot.datasource.pojo.DictConfigDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DictConfigMapperTest {

    @Autowired
    private DictConfigMapper dictConfigMapper;

    @Test
    public void testSelectById() {
        DictConfigDO dictConfigDO = dictConfigMapper.selectById(845960218868187136L);
        System.out.println(dictConfigDO);
    }

    @Test
    public void testBroadcastInsert(){
        DictConfigDO dictConfigDO = new DictConfigDO();
        dictConfigDO.setDictType(22);
        dictConfigMapper.insert(dictConfigDO);
    }

    @Test
    public void testBroadcastUpdate(){
        DictConfigDO dictConfigDO = new DictConfigDO();
        dictConfigDO.setId(845960218868187136L);
        dictConfigDO.setDictType(11);
        dictConfigMapper.updateById(dictConfigDO);
    }

}
