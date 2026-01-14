package com.cc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cc.lab9904.Lab9904Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = Lab9904Application.class)
public class TestDuoLinGo {

    @Test
    public void test01() {

        String inputFile = "1-1621.json"; // 您的JSON文件路径
        String outputFile = "output.txt"; // 输出的TXT文件路径

        try {
            convertJsonToTxtAdvanced(inputFile, outputFile);
            System.out.println("转换完成！输出文件: " + outputFile);
        } catch (Exception e) {
            System.err.println("转换过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 可选：使用路径表达式直接获取数据（更简洁的写法）
    public static void convertJsonToTxtAdvanced(String inputFile, String outputFile) {
        String jsonContent = FileUtil.readUtf8String(inputFile);
        JSONObject rootObj = JSONUtil.parseObj(jsonContent);

        StringBuilder txtContent = new StringBuilder();
        txtContent.append("text\ttranslations\taudio_url\n");

        // 使用路径表达式获取learnedLexemes数组[3](@ref)
        JSONArray lexemesArray = rootObj.getByPath("learnedLexemes", JSONArray.class);
        if (lexemesArray != null) {
            for (int i = 0; i < lexemesArray.size(); i++) {
                String text = lexemesArray.getJSONObject(i).getByPath("text", String.class);
                String audioUrl = lexemesArray.getJSONObject(i).getByPath("audioURL", String.class);
                // 处理translations数组
                JSONArray translations = lexemesArray.getJSONObject(i).getByPath("translations", JSONArray.class);
                String translationsStr = translations != null ?
                        String.join(";", translations.toList(String.class)) : "";

                txtContent.append(text).append("\t")
                        .append(translationsStr).append("\t")
                        .append(audioUrl).append("\n");
            }
        }

        FileUtil.writeUtf8String(txtContent.toString(), outputFile);
    }
}
