package com.cc.lab9902.test06.license;

import cn.hutool.core.util.StrUtil;
import com.cc.lab9902.test06.utils.LicenseInfoUtil;
import com.cc.lab9902.test06.utils.LicenseUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-28 18:43
 */
@Component
public class ValidateLicenseService implements CommandLineRunner {
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    Environment environment;

    @Override
    public void run(String... args) throws Exception {
        String license = environment.getProperty("license");
        System.out.println(license);
        boolean isValid = false;
        if (StrUtil.isNotBlank(license)) {
            List<String> macAddressList = LicenseInfoUtil.getMacAddressList();
            for (String macAddress : macAddressList) {
                boolean b = LicenseUtil.validateLicenseNumber01(license, macAddress, "eleven");
                if (b) {
                    isValid = true;
                    break;
                }
            }
        }
        if (!isValid) {
            // 匹配不成功，关闭应用上下文，停止项目启动
            ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
            context.close();
            System.out.println("授权码无效或已过期，程序启动失败！");
            return;
        }
        System.out.println("程序启动成功！");
    }
}
