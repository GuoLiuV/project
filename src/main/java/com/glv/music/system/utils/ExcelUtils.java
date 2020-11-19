package com.glv.music.system.utils;

import com.glv.music.system.modules.excel.dto.TableData;
import com.glv.music.system.modules.excel.utils.StriveExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

/**
 * 操作文档的工具类
 *
 * @author ZHOUXIANG
 */
public class ExcelUtils {

    public static <T> void writeExcel(XSSFWorkbook wb, String sheetName, List<T> list) {
        StriveExcelUtil.writeExcel(wb, sheetName, list);
    }

    public static <T> ByteArrayOutputStream writeExcel(List<T> list) throws Exception {
        return writeExcel(null, list);
    }

    public static <T> void writeExcelToFile(List<T> list, File file) throws Exception {
        writeExcelToFile(null, list, file);
    }

    public static <T> void writeExcelToFile(String sheetName, List<T> list, File file) throws Exception {
        StriveExcelUtil.writeExcelToFile(sheetName, list, file);
    }

    public static <T> void writeMoreSheetExcelToFile(String sheetName, List<T> list, File file) throws Exception {
        StriveExcelUtil.writeMoreSheetExcelToFile(sheetName, list, file, 0, 0);
    }

    public static <T> void writeMoreSheetExcelToFile(String sheetName, List<T> list, File file, int startRow, int startCol) throws Exception {
        StriveExcelUtil.writeMoreSheetExcelToFile(sheetName, list, file, startRow, startCol);
    }

    public static <T> ByteArrayOutputStream writeExcel(String sheetName, List<T> list) throws Exception {
        return StriveExcelUtil.writeExcel(sheetName, list);
    }

    public static ByteArrayOutputStream writeExcel(TableData data) throws IOException {
        return StriveExcelUtil.writeExcel(data);
    }

    public static void addPicture(File xlsxFile, byte[] imageData,
                                  int col1, int row1, int col2, int row2) throws IOException {
        StriveExcelUtil.addPicture(xlsxFile, imageData, col1, row1, col2, row2);
    }

    public static <T> List<T> readExcel(Class<T> clazz, InputStream in) throws Exception {
        return StriveExcelUtil.readExcel(clazz, in);
    }

    public static <T> List<T> readExcel(Class<T> clazz, File file) throws Exception {
        return StriveExcelUtil.readExcel(clazz, new FileInputStream(file));
    }

    public static TableData readExcel(InputStream in) throws Exception {
        return StriveExcelUtil.readExcel(in);
    }
}
