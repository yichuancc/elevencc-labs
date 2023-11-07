package cn.elevencc.springboot.datasource.service;

import cn.elevencc.springboot.datasource.mapper.OrderMapper;
import cn.elevencc.springboot.datasource.pojo.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public void add(OrderDO order) {
        // 插入订单
        orderMapper.insert(order);

        OrderDO exists = orderMapper.selectById(1);
        System.out.println(exists);
    }

}
