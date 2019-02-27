package com.java;

import java.io.Serializable;

import java.util.Date;


public class ${table.className} implements Serializable {


<#list table.columns as column>
    private ${column.javaType} ${column.fileName};
</#list>
    public String getName() {
    return name;
    }
<#list table.columns as column>
    public void set${column.upperFileName}(${column.javaType} ${column.fileName}) {
    this.${column.fileName} = ${column.fileName};
    }

    public ${column.javaType} get${column.upperFileName}() {
    return ${column.fileName};
    }
</#list>

}
