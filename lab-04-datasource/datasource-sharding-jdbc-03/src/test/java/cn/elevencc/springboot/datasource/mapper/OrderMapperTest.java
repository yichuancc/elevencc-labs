package cn.elevencc.springboot.datasource.mapper;

import cn.elevencc.springboot.datasource.Application;
import cn.elevencc.springboot.datasource.pojo.OrderDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSelectById() {
        OrderDO order = orderMapper.selectById(1L);
        System.out.println(order);
    }

    @Test
    public void selectListAll() {
        List<OrderDO> orders = orderMapper.selectListAll();
        System.out.println(orders.size());
    }

    @Test
    public void testSelectListByUserId() {
        List<OrderDO> orders = orderMapper.selectListByUserId(1);
        System.out.println(orders.size());
    }

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++){
            OrderDO order = new OrderDO();
            order.setUserId(i);
            orderMapper.insert(order);
        }

    }

}
