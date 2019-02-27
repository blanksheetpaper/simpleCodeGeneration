package com.java.bean;

public class Column {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 属性名
     */
    private String fileName;
    /**
     * 首字母大写的类名
     */
    private String upperFileName;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * 对应的Java类型
     */
    private String javaType;
    /**
     * 备注/注释
     */
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUpperFileName() {
        return upperFileName;
    }

    public void setUpperFileName(String upperFileName) {
        this.upperFileName = upperFileName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}
