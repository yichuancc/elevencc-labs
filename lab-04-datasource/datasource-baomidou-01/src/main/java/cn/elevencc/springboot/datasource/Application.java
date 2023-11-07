package cn.elevencc.springboot.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * MapperScan注解  Mapper 接口所在的包路径。
 * EnableAspectJAutoProxy 注解，配置 exposeProxy = true，希望 Spring AOP 能将当前代理对象设置到 AopContext 中
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.elevencc.springboot.datasource.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class Application {
}
