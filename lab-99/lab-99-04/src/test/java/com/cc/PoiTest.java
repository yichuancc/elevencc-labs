package com.cc;


import com.cc.lab9904.Lab9904Application;
import com.cc.lab9904.entity.AuthorInfo;
import com.cc.lab9904.entity.DataInfo;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.BorderStyle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = Lab9904Application.class)
public class PoiTest {


    @Test
    public void testText() throws Exception {
        ClassPathResource resource = new ClassPathResource("template.docx");
        XWPFTemplate template = XWPFTemplate.compile(resource.getInputStream()).render(
                new HashMap<String, Object>() {{
                    put("title", "Hi, poi-tl Word模板引擎");
                }});
        template.writeAndClose(new FileOutputStream("output.docx"));
    }

    @Test
    public void testText2() throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Sayi");
        data.put("start_time", "2019-08-04");

        ClassPathResource resource = new ClassPathResource("template.docx");
        XWPFTemplate template = XWPFTemplate.compile(resource.getInputStream()).render(data);
        template.writeAndClose(new FileOutputStream("output.docx"));
    }

    @Test
    public void testEntity() throws IOException {
        Map<String, Object> data = new HashMap<>();
        DataInfo dataInfo = new DataInfo();
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.setName("11");
        dataInfo.setName("eleven");
        dataInfo.setAuthor(authorInfo);
        data.put("name", "Sayi");
        data.put("start_time", "2019-08-04");
        data.put("data", dataInfo);

        ClassPathResource resource = new ClassPathResource("template.docx");
        XWPFTemplate template = XWPFTemplate.compile(resource.getInputStream()).render(data);
        template.writeAndClose(new FileOutputStream("output.docx"));
    }

    @Test
    public void test01() throws IOException {
        Map<String, Object> data = new HashMap<>();

        // 普通文本 {{var}}
        data.put("title", "Hi, poi-tl Word模板引擎");
        data.put("name", "Sayi");
        data.put("start_time", "2019-08-04");

        // 对象  {{data.author.name}}
        DataInfo dataInfo = new DataInfo();
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.setName("11");
        dataInfo.setName("eleven");
        dataInfo.setAuthor(authorInfo);
        data.put("data", dataInfo);

        // 文本样式  {{var}}
        data.put("name1", "Sayi");
        data.put("author1", Texts.of("Sayi").color("000000").create());
        data.put("link1", Texts.of("website").link("http://deepoove.com").create());
        data.put("anchor1", Texts.of("anchortxt").anchor("appendix1").create());

        // 图片 图片标签以@开始：{{@var}}
        // 指定图片路径
        String imageUrl = "src/main/resources/logo.png";
        data.put("image", imageUrl);
        // svg图片
        //data.put("svg", "https://img.shields.io/badge/jdk-1.6%2B-orange.svg");

        // 图片文件
        data.put("image1", Pictures.ofLocal(imageUrl).size(120, 120).create());

        // 图片流
//        data.put("streamImg", Pictures.ofStream(new FileInputStream("logo.jpeg"), PictureType.JPEG)
//                .size(100, 120).create());

        // 网络图片(注意网络耗时对系统可能的性能影响)
        data.put("urlImg", Pictures.ofUrl("http://deepoove.com/images/icecream.png")
                .size(100, 100).create());

        // java图片，我们可以利用Java生成图表插入到word文档中
//        data.put("buffered", Pictures.ofBufferedImage(bufferImage, PictureType.PNG)
//               .size(100, 100).create());


        // 表格 表格标签以#开始：{{#var}}
        // 1. 基础表格示例
        // 一个2行2列的表格
        data.put("table0", Tables.of(new String[][]{
                new String[]{"00", "01"},
                new String[]{"10", "11"}
        }).border(BorderStyle.DEFAULT).create());
        // 2. 表格样式示例
        // 第0行居中且背景为蓝色的表格
        RowRenderData row0 = Rows.of("姓名", "学历").textColor("FFFFFF")
                .bgColor("4472C4").center().create();
        RowRenderData row1 = Rows.create("李四", "博士");
        data.put("table1", Tables.create(row0, row1));

        // 3. 表格合并示例
        // 合并第1行所有单元格的表格
        RowRenderData row3 = Rows.of("列0", "列1", "列2").center().bgColor("4472C4").create();
        RowRenderData row4 = Rows.create("没有数据", null, null);
        MergeCellRule rule = MergeCellRule.builder().map(MergeCellRule.Grid.of(1, 0), MergeCellRule.Grid.of(1, 2)).build();
        data.put("table3", Tables.of(row3, row4).mergeRule(rule).create());

        // 列表 列表标签以*开始：{{*var}}
        data.put("list", Numberings.create("Plug-in grammar",
                "Supports word text, pictures, table...",
                "Not just templates"));

        ClassPathResource resource = new ClassPathResource("template.docx");
        XWPFTemplate template = XWPFTemplate.compile(resource.getInputStream()).render(data);
        template.writeAndClose(new FileOutputStream("output.docx"));
    }
}
