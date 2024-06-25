package com.cc.lab9901.test03.demo01;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-25 16:44
 */
public class ReadabilityDemo {
    public static void main(String[] args) {
        // 扩展易读性
        new ReadabilityDemo().lambdaReadability();
    }

    public void lambdaReadability() {
        List<String> list = Arrays.asList("a", "bb", "ccc", "dd", "eee");
        List<String> result = list.stream()
                .filter(this::isLongerThanOne)
                .map(this::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("提升可读性的Lambda操作结果: " + result);
    }

    /**
     * 判断字符串长度是否大于1
     *
     * @param s
     * @return
     */
    private boolean isLongerThanOne(String s) {
        return s.length() > 1;
    }

    /**
     * 将字符串转换为大写
     *
     * @param s
     * @return
     */
    private String toUpperCase(String s) {
        return s.toUpperCase();
    }
}
