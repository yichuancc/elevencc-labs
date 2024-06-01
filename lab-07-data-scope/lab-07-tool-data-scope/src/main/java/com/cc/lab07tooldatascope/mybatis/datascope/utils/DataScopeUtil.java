package com.cc.lab07tooldatascope.mybatis.datascope.utils;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cc.lab07tooldatascope.mybatis.datascope.constant.DataScopeConstant;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class DataScopeUtil {


    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(CharSequence template, Object... params) {
        if (null == template) {
            return null;
        }
        if (ObjUtil.isEmpty(params) || isBlank(template)) {
            return template.toString();
        }
        return StrFormatter.format(template.toString(), params);
    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(CharSequence str, CharSequence suffix) {
        if (StrUtil.isEmpty(str) || StrUtil.isEmpty(suffix)) {
            return StringPool.EMPTY;
        }

        final String str2 = str.toString();
        if (str2.endsWith(suffix.toString())) {
            return subPre(str2, str2.length() - suffix.length());
        }
        return str2;
    }

    /**
     * 切割指定位置之前部分的字符串
     *
     * @param string  字符串
     * @param toIndex 切割到的位置（不包括）
     * @return 切割后的剩余的前半部分字符串
     */
    public static String subPre(CharSequence string, int toIndex) {
        return sub(string, 0, toIndex);
    }

    /**
     * 改进JDK subString<br>
     * index从0开始计算，最后一个字符为-1<br>
     * 如果from和to位置一样，返回 "" <br>
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
     * 如果经过修正的index中from大于to，则互换from和to example: <br>
     * abcdefgh 2 3 =》 c <br>
     * abcdefgh 2 -3 =》 cde <br>
     *
     * @param str       String
     * @param fromIndex 开始的index（包括）
     * @param toIndex   结束的index（不包括）
     * @return 字串
     */
    public static String sub(CharSequence str, int fromIndex, int toIndex) {
        if (StrUtil.isEmpty(str)) {
            return StringPool.EMPTY;
        }
        int len = str.length();

        if (fromIndex < 0) {
            fromIndex = len + fromIndex;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        } else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return StringPool.EMPTY;
        }

        return str.toString().substring(fromIndex, toIndex);
    }

    /**
     * 判断是否为空
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        return !org.springframework.util.StringUtils.hasText(cs);
    }

    /**
     * 判断是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(@Nullable Object[] array) {
        return org.springframework.util.ObjectUtils.isEmpty(array);
    }

    /**
     * 返回集合元素添加逗号分隔符的字符串
     *
     * @param coll
     * @return
     */
    public static String join(Collection<?> coll) {
        return org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
    }

    /**
     * 给集合元素添加单引号
     *
     * @param coll
     * @return
     */
    public static Set<String> joinSingleQuotes(Set<String> coll) {
        // 为列表中的每个字符串添加单引号
        Set<String> strSet = new HashSet<>();
        for (String str : coll) {
            strSet.add("'" + str + "'");
        }
        return strSet;
    }

    /**
     * 强转string,并去掉多余空格
     *
     * @param str 字符串
     * @return String
     */
    public static String toStr(Object str) {
        return toStr(str, "");
    }

    /**
     * 强转string,并去掉多余空格
     *
     * @param str          字符串
     * @param defaultValue 默认值
     * @return String
     */
    public static String toStr(Object str, String defaultValue) {
        if (null == str) {
            return defaultValue;
        }
        return String.valueOf(str);
    }

    /**
     * 首字母变小写
     *
     * @param str 字符串
     * @return {String}
     */
    public static String firstCharToLower(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= DataScopeConstant.UPPER_A && firstChar <= DataScopeConstant.UPPER_Z) {
            char[] arr = str.toCharArray();
            arr[0] += (DataScopeConstant.LOWER_A - DataScopeConstant.UPPER_A);
            return new String(arr);
        }
        return str;
    }

    /**
     * 格式化字符串<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： 	format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\：		format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param strPattern 字符串模板
     * @param argArray   参数列表
     * @return 结果
     */
    public static String format(final String strPattern, final Object... argArray) {
        if (isBlank(strPattern) || isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        /**
         * 初始化定义好的长度以获得更好的性能
         */
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        /**
         * 记录已经处理到的位置
         */
        int handledPosition = 0;

        /**
         * 占位符所在位置
         */
        int delimIndex;
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(DataScopeConstant.EMPTY_JSON, handledPosition);
            /**
             * 剩余部分无占位符
             */
            if (delimIndex == -1) {
                /**
                 * 不带占位符的模板直接返回
                 */
                if (handledPosition == 0) {
                    return strPattern;
                } else {
                    sbuf.append(strPattern, handledPosition, strPatternLength);
                    return sbuf.toString();
                }
            } else {
                /**
                 * 转义符
                 */
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == DataScopeConstant.BACK_SLASH) {
                    /**
                     * 双转义符
                     */
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == DataScopeConstant.BACK_SLASH) {
                        //转义符之前还有一个转义符，占位符依旧有效
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(toStr(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    } else {
                        //占位符被转义
                        argIndex--;
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(StringPool.LEFT_BRACE);
                        handledPosition = delimIndex + 1;
                    }
                } else {//正常占位符
                    sbuf.append(strPattern, handledPosition, delimIndex);
                    sbuf.append(toStr(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }
        }
        //加入最后一个占位符后所有的字符
        sbuf.append(strPattern, handledPosition, strPattern.length());
        return sbuf.toString();
    }

}
