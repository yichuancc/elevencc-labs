package com.cc.lab9901.test01;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReUtil;
import parsii.tokenizer.ParseException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-05 21:37
 */
public class Test01 {

    public static void main(String[] args) throws ParseException {


//        String exp = "2 + (7-5) * 3.14159 * x + sin(0)";
        String exp = "e^0.4*sign(3/12)";
        exp = "((1＋2 －   0) × 100 ＋( 2X1)－2 ) ÷2 * 50.5% * 20% + {1－2} + [1＋5] ﹣1·1 /1 ﹢1 ﹣1";
//        String exp = "e^0.4*√(3/12)";


        System.out.println("之前:" + exp);
        //exp = Test01.transitionFormula(exp);
        System.out.println("之后:" + exp);

// compile
//        Scope scope = new Scope();
//        Expression parsiiExpr = Parser.parse(exp);
//        Variable var = scope.getVariable("x");
//        var.setValue(0);

// evaluate
//        BigDecimal result = BigDecimal.valueOf(parsiiExpr.evaluate());

        //       System.out.println("计算结果:" + result);//-> 2.0
    }

    public static String transitionFormula(String exp) {

//        exp = "＋";
        //全角转半角
        exp = Convert.toDBC(exp);
        //所有字符转为小写
        exp = exp.toLowerCase();


        exp = exp
                .replaceAll("﹢", "+")
                .replaceAll("十", "+")
                .replaceAll("┼", "+")
                .replaceAll("╋", "+")
                .replaceAll("╬", "+")
                .replaceAll("﹣", "-")
                .replaceAll("一", "-")
                .replaceAll("x", "*")
                .replaceAll("×", "*")
                .replaceAll("╳", "*")
                .replaceAll("х", "*")
                .replaceAll("·", "*")
                .replaceAll("÷", "/")
                .replaceAll("\\{", "(")
                .replaceAll("}", ")")
                .replaceAll("\\[", "(")
                .replaceAll("]", ")")
        ;

        //包含百分号
        if (exp.contains("%")) {
            //获取包含百分号的数字
            //List<String> resultFindAll = ReUtil.findAll("([\\d])*%|([\\d]).([\\d])*%", exp, 0, new ArrayList<String>());
            List<String> resultFindAll = ReUtil.findAll("([\\\\d])*%|(\\\\d{1,10}).([\\\\d])*%", exp, 0, new ArrayList<String>());
            if (CollUtil.isNotEmpty(resultFindAll)) {
                for (String s : resultFindAll) {
                    s = s.trim();
                    String replace = s.replace("%", "");
                    BigDecimal bigDecimal = new BigDecimal(replace);
                    bigDecimal = bigDecimal.divide(new BigDecimal(100));
                    exp = exp.replaceAll(s, bigDecimal.toString());

                }
            }
            System.out.println(resultFindAll);
        }
        return exp;
    }


}
