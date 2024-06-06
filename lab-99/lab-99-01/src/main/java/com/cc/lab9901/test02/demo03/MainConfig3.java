package com.cc.lab9901.test02.demo03;

import com.cc.lab9901.test02.demo03.module1.ComponentScanModule1;
import com.cc.lab9901.test02.demo03.module2.ComponentScanModule2;
import org.springframework.context.annotation.Import;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-06 21:23
 */
@Import({ComponentScanModule1.class, ComponentScanModule2.class})
public class MainConfig3 {
}
