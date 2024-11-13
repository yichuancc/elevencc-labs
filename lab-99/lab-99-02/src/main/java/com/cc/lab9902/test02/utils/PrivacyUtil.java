package com.cc.lab9902.test02.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-13 13:15
 */
public class PrivacyUtil {

    /**
     * 中文名脱敏
     */
    public static String hideChineseName(String chineseName) {
        if (StrUtil.isEmpty(chineseName)) {
            return null;
        }
        if (chineseName.length() <= 2) {
            return desValue(chineseName, 1, 0, "*");
        }
        return desValue(chineseName, 1, 1, "*");
    }

    /**
     * 手机号脱敏
     */
    public static String hidePhone(String phone) {
        if (StrUtil.isEmpty(phone)) {
            return null;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"); // 隐藏中间4位
//        return desValue(phone, 3, 4, "*"); // 隐藏中间4位
//        return desValue(phone, 7, 0, "*"); // 隐藏末尾4位
    }

    /**
     * 邮箱脱敏
     */
    public static String hideEmail(String email) {
        if (StrUtil.isEmpty(email)) {
            return null;
        }
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

    /**
     * 身份证号脱敏
     */
    public static String hideIDCard(String idCard) {
        if (StrUtil.isEmpty(idCard)) {
            return null;
        }
        return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
    }

    /**
     * 对字符串进行脱敏操作
     *
     * @param origin          原始字符串
     * @param prefixNoMaskLen 左侧需要保留几位明文字段
     * @param suffixNoMaskLen 右侧需要保留几位明文字段
     * @param maskStr         用于遮罩的字符串, 如'*'
     * @return 脱敏后结果
     */
    public static String desValue(String origin, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
        if (StrUtil.isEmpty(origin)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = origin.length(); i < n; i++) {
            if (i < prefixNoMaskLen) {
                sb.append(origin.charAt(i));
                continue;
            }
            if (i > (n - suffixNoMaskLen - 1)) {
                sb.append(origin.charAt(i));
                continue;
            }
            sb.append(maskStr);
        }
        return sb.toString();
    }

}
