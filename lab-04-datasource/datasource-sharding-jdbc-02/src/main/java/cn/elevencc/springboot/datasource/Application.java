package cn.elevencc.springboot.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(basePackages = "cn.elevencc.springboot.datasource.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class Application {
}
