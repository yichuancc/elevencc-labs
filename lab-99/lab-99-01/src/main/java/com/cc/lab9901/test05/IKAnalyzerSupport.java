package com.cc.lab9901.test05;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: elevencc-labs
 * @description:
 *                  https://www.cnblogs.com/liuyanntes/p/12540696.html
 *                  https://blog.csdn.net/qq_71387716/article/details/140404886
 * @author: yic
 * @create: 2024-08-16 10:49
 */
@Slf4j
public class IKAnalyzerSupport {
    /**
     * IK分词
     *
     * @param target
     * @return
     */
    public static List<String> iKSegmenterToList(String target) throws Exception {
        if (StringUtils.isEmpty(target)) {
            return Lists.newArrayList();
        }
        List<String> result = new ArrayList<>();
        StringReader sr = new StringReader(target);
        // 关闭智能分词 (对分词的精度影响较大)
        IKSegmenter ik = new IKSegmenter(sr, false);
        Lexeme lex;
        while ((lex = ik.next()) != null) {
            String lexemeText = lex.getLexemeText();
            result.add(lexemeText);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String str = "http://www.relaxheart.cn 是xx同学的个人兴趣分享网站";
        System.out.println(iKSegmenterToList(str));
    }
}
