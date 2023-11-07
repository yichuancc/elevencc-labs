package cn.elevencc.springboot.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.elevencc.springboot.datasource.mapper")
public class Application {
}
