package com.java.utils;

import com.java.bean.Column;
import com.java.bean.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库映射生成POJO工具类
 *
 * @author admin
 */
public class PropertiesUtils {
    /**
     * 驱动类名
     */
    private static String driver;
    /**
     * 链接url
     */
    private static String url;
    /**
     * 用户名
     */
    private static String user;
    /**
     * 密码
     */
    private static String password;

    /**
     * 连接对象
     */
    private static Connection connection;
    /**
     * 数据库元数据
     */
    private static DatabaseMetaData metaData;

    private PropertiesUtils() {
    }

    //初始化环境
    static {
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        driver = bundle.getString("jdbc.driver");
        url = bundle.getString("jdbc.url");
        user = bundle.getString("jdbc.user");
        password = bundle.getString("jdbc.password");
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            metaData = connection.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 获取数据库中所有的表名字
     * @throws Exception
     */
    public static List<Table> getTable() throws Exception {
        List<Table> list = new ArrayList<>();
        //获取表名字
        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
        //遍历结果集
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            Table table = new Table();
            //设置表名
            table.setTableName(tableName);
            tableName = tableName.toLowerCase();
            //将下划线后面的首字目变为大写
            String className = toConversionWithUnderline(tableName, true);
            //设置类名
            table.setClassName(className);
            //设置列
            List<Column> columns = getColumn(tableName);
            table.setColumns(columns);
            list.add(table);
        }
        return list;

    }


    /**
     * @return 返回数据库表里面的所有的列名
     * @throws Exception
     */

    public static List<Column> getColumn(String tableName) throws Exception {
        List<Column> list = new ArrayList<>();
        //获取列明
        ResultSet columns = metaData.getColumns(null, "%", tableName, "%");
        while (columns.next()) {
            Column column = new Column();
            //获取列明
            String column_name = columns.getString("COLUMN_NAME");
            column.setColumnName(column_name);
            //获取列的类型
            String type_name = columns.getString("TYPE_NAME");
            column.setColumnType(type_name);
            //获取列的注释
            String remarks = columns.getString("REMARKS");
            column.setRemarks(remarks);
            //属性名
            String fileName = column_name;
            if (column_name.contains("_")) {
                fileName = toConversionWithUnderline(column_name, false);
            }
            column.setFileName(fileName);
            //首字母大写字段
            String upper = firstUpper(fileName);
            column.setUpperFileName(upper);
            //数据类型的转化
            String javaType = swtichType(type_name);
            column.setJavaType(javaType);
            list.add(column);
        }
        return list;
    }


    /**
     * 将下划线后面的字符大写
     * 例如  user_info  ------>  userInfo
     *
     * @param name             需要转化的名字
     * @param needFirstToUpper 是否需要首字符也转化为大写
     * @return
     */
    public static String toConversionWithUnderline(String name, boolean needFirstToUpper) {
        StringBuffer stringBuffer = new StringBuffer();
        Pattern pattern = Pattern.compile("_(\\w)");
        Matcher matcher = pattern.matcher(name);
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }
        String className = matcher.appendTail(stringBuffer).toString();
        if (needFirstToUpper) {
            className = firstUpper(className);
        }
        return className;
    }


    /**
     * 将首字母转化为大写；例如 user  ----> User
     *
     * @param string 需要转化首字大写的字符串
     * @return 首字母转化为大写的字符串
     */
    public static String firstUpper(String string) {
        char[] cs = string.toCharArray();
        cs[0] -= 32;
        string = String.valueOf(cs);
        return string;
    }


    /**
     * 将对应的数据库类型转化为对应的Java类型
     *
     * @param columnType 数据库列类型
     * @return Java对应的数据类型（只写了部分没有写全）
     */
    public static String swtichType(String columnType) {
        String javaType;
        switch (columnType) {
            case "VARCHAR":
                javaType = "String";
                break;
            case "BIGINT":
                javaType = "Long";
                break;
            case "INT":
                javaType = "Integer";
                break;
            case "DATETIME":
                javaType = "Date";
                break;
            case "DATE":
                javaType = "Date";
                break;
            default:
                javaType = "String";
                break;
        }
        return javaType;
    }


}
