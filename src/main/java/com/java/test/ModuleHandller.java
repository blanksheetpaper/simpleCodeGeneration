package com.java.test;

import com.java.bean.Table;
import com.java.utils.PropertiesUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行测试生成数据库中的表字段
 */
public class ModuleHandller {

    //    @Test 注意：此处不能使用此注解，因为如果使用此注解，获得的path就是test路径下面的类路径，
    public void exctue() throws Exception {
        //获取他变了实例
        List<Table> list = PropertiesUtils.getTable();
        Map<String, Object> map = new HashMap();
        for (Table table : list
        ) {
            map.put("table", table);
            //初始化
            Configuration configuration = new Configuration(Configuration.getVersion());
            //获取模板所在的文件夹位置
            String path = this.getClass().getClassLoader().getResource("").getPath();
            System.out.println(path);
            configuration.setDirectoryForTemplateLoading(new File(path));
            //获取模板对象
            Template template = configuration.getTemplate("test.ftl");
            //执行代码
            OutputStream file = new FileOutputStream(path + table.getClassName() + ".java");
            Writer out = new OutputStreamWriter(file);
            template.process(map, out);
            out.close();
        }
    }

    public static void main(String[] args) throws Exception {
        ModuleHandller m = new ModuleHandller();
        m.exctue();
    }
}
