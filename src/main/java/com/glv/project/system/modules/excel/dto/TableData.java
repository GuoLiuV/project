package com.glv.project.system.modules.excel.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * @author ZHOUXIANG
 */
public class TableData {

    public String sheetName;

    public List<String> headers;

    public List<List<String>> contents;

    public TableData() {
        headers=new ArrayList<>();
        contents=new ArrayList<>();
    }

    @Override
    public String toString() {
        return "TableData [sheetName=" + sheetName + ", headers=" + headers + ", contents=" + contents + "]";
    }

}

