package cn.elevencc.springboot.datasource.service;

import cn.elevencc.springboot.datasource.mapper.OrderMapper;
import cn.elevencc.springboot.datasource.constant.DBConstants;
import cn.elevencc.springboot.datasource.pojo.OrderDO;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    @DS(DBConstants.DATASOURCE_MASTER)
    public void add(OrderDO order) {
        // 这里先模拟读取一下
        OrderDO exists = orderMapper.selectById(order.getId());
        System.out.println(exists);

        // 插入订单
        orderMapper.insert(order);
    }

}
