package cn.elevencc.springboot.datasource.service;

import cn.elevencc.springboot.datasource.pojo.OrderDO;
import cn.elevencc.springboot.datasource.pojo.UserDO;
import cn.elevencc.springboot.datasource.mapper.OrderMapper;
import cn.elevencc.springboot.datasource.mapper.UserMapper;
import cn.elevencc.springboot.datasource.constant.DBConstants;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * self() 方法，通过 AopContext 获得自己这个代理对象。
     * 例如，在 #method01() 方法中，如果直接使用 this.method02() 方法进行调用，
     * 因为 this 代表的是 OrderService Bean 自身，而不是其 AOP 代理对象。
     * 会导致，无法触发 AOP 的逻辑，此处，就是 Spring 事务的逻辑。因此，通过 AopContext 获得自己这个代理对象。
     *
     */
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

    @Transactional
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

    @Transactional
    public void method031() {
        // 报错，此时获取的DataSource ，是users
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
    }

    @Transactional
    public void method032() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }

    public void method04() {
        // 查询订单
        self().method041();
        // 查询用户
        self().method042();
    }

    @Transactional
    @DS(DBConstants.DATASOURCE_ORDERS)
    public void method041() {
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
    }

    @Transactional
    @DS(DBConstants.DATASOURCE_USERS)
    public void method042() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Transactional
    @DS(DBConstants.DATASOURCE_ORDERS)
    public void method05() {
        // 查询订单
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
        // 查询用户
        self().method052();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DS(DBConstants.DATASOURCE_USERS)
    public void method052() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }

}
