package cn.elevencc.springboot.datasource.service;

import cn.elevencc.springboot.datasource.constant.DBConstants;
import cn.elevencc.springboot.datasource.mapper.orders.OrderMapper;
import cn.elevencc.springboot.datasource.mapper.users.UserMapper;
import cn.elevencc.springboot.datasource.pojo.OrderDO;
import cn.elevencc.springboot.datasource.pojo.UserDO;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    private OrderService self() {
        return (OrderService) AopContext.currentProxy();
    }

    public void method01() {
        // 查询订单
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
        // 查询用户
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Transactional // 报错，找不到事务管理器
    public void method02() {
        // 查询订单
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
        // 查询用户
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }

    public void method03() {
        // 查询订单
        self().method031();
        // 查询用户
        self().method032();
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_ORDERS)
    public void method031() {
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_USERS)
    public void method032() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }
}
