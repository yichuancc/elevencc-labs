package com.cc.lab9902.test03.util;

import cn.hutool.core.util.StrUtil;


/**
 * 脱敏工具类
 *
 * @author cc
 */
public class PrivacyUtil {

    /**
     * 中文名脱敏
     */
    public static String hideChineseName(String chineseName) {
        if (StrUtil.isEmpty(chineseName)) {
            return "";
        }
        return desValue(chineseName, 1, 1, "*");
    }

    /**
     * 身份证号脱敏
     */
    public static String hideIDCard(String idCard) {
        if (StrUtil.isEmpty(idCard)) {
            return "";
        }
        return desValue(idCard, 4, 4, "*");
    }

    /**
     * 手机号脱敏
     */
    public static String hidePhone(String phone) {
        if (StrUtil.isEmpty(phone)) {
            return "";
        }
        return desValue(phone, 3, 4, "*");
    }

    /**
     * 银行卡号脱敏
     */
    public static String hideBankCard(String bankCard) {
        if (StrUtil.isEmpty(bankCard)) {
            return "";
        }
        return desValue(bankCard, 6, 2, "*");
    }

    /**
     * 邮箱脱敏
     */
    public static String hideEmail(String email) {
        if (StrUtil.isEmpty(email)) {
            return "";
        }
        // 正则表达式 example@gmail.com 脱敏之后为 e****@gmail.com
        return email.replaceAll("(^.)[^@]*(@.*$)", "$1****$2");
    }

    /**
     * 对字符串进行脱敏操作
     *
     * @param origin          原始字符串
     * @param prefixNoMaskLen 前缀保留长度
     * @param suffixNoMaskLen 后缀保留长度
     * @param maskStr         替换符
     * @return 脱敏后结果
     */
    public static String desValue(String origin, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
        int length = origin.length();
        if (StrUtil.isEmpty(origin)) {
            return "";
        }
        // 情况一：原始字符串长度小于等于保留长度，则原始字符串全部替换
        if (prefixNoMaskLen >= length || suffixNoMaskLen >= length) {
            return buildReplacerByLength(maskStr, length);
        }

        // 情况二：原始字符串长度小于等于前后缀保留字符串长度，则原始字符串全部替换
        if ((prefixNoMaskLen + suffixNoMaskLen) >= length) {
            return buildReplacerByLength(maskStr, length);
        }

        // 情况三：原始字符串长度大于前后缀保留字符串长度，则替换中间字符串
        int interval = length - prefixNoMaskLen - suffixNoMaskLen;
        return origin.substring(0, prefixNoMaskLen) +
                buildReplacerByLength(maskStr, interval) +
                origin.substring(prefixNoMaskLen + interval);
    }

    /**
     * 根据长度循环构建替换符
     *
     * @param replacer 替换符
     * @param length   长度
     * @return 构建后的替换符
     */
    private static String buildReplacerByLength(String replacer, int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(replacer);
        }
        return builder.toString();
    }

}
