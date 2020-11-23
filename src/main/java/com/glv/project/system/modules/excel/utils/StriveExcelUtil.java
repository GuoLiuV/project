package com.glv.project.system.modules.excel.utils;

import com.glv.project.system.modules.excel.annotation.ExcelCell;
import com.glv.project.system.modules.excel.dto.TableData;
import com.glv.project.system.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class StriveExcelUtil {

    private static final Class<?>[] SUPPORT_FIELD_CLASSES = {
            int.class, Integer.class,
            long.class, Long.class,
            short.class, Short.class,
            byte.class, Byte.class,
            boolean.class, Boolean.class,
            float.class, Float.class,
            double.class, Double.class,
            Date.class,
            String.class
    };

    public static class Coordinate {
        public int x;
        public int y;

        public Coordinate() {

        }

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static <T> void writeExcel(XSSFWorkbook wb, String sheetName, List<T> list) {
        writeExcel0(wb, sheetName, list);
    }

    private static <T> void writeExcel0(XSSFWorkbook wb, String sheetName, List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        try {
            T test = list.get(0);
            Map<String, Field> fieldMap = new HashMap<>();
            List<String> titles = new ArrayList<>();
            Field[] fields = test.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelCell.class)) {
                    checkFieldClass(field.getType());
                    ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
                    fieldMap.put(excelCell.name(), field);
                    titles.add(excelCell.name());
                }
            }
            if (fieldMap.size() != titles.size()) {
                throw new IllegalArgumentException("@ExcelCell name cannot same");
            }
            //
            Sheet sheet;
            if (sheetName == null) {
                sheet = wb.createSheet();
            } else {
                sheet = wb.createSheet(sheetName);
            }
            //set header
            Row titleRow = sheet.createRow(0);
            for (int i = 0; i < titles.size(); i++) {
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(titles.get(i));
            }
            //
            int totalRowCount = list.size();
            for (int r = 0; r < totalRowCount; r++) {
                T bean = list.get(r);
                Row row = sheet.createRow(r + 1);
                for (int c = 0; c < titles.size(); c++) {
                    Cell cell = row.createCell(c);
                    String title = titles.get(c);
                    Field field = fieldMap.get(title);
                    field.setAccessible(true);
                    Object val = field.get(bean);
                    if (val == null) {
                        continue;
                    }
                    ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
                    if (field.getType() == Date.class) {
                        cell.setCellValue(new SimpleDateFormat(excelCell.dateFormat()).format((Date) val));
                    } else {
                        cell.setCellValue(val.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new POIXMLException(e.getMessage(), e);
        }

    }

    public static <T> ByteArrayOutputStream writeExcel(List<T> list) throws Exception {
        return writeExcel(null, list);
    }


    public static <T> void writeExcelToFile(List<T> list, File file) throws Exception {
        writeExcelToFile(null, list, file);
    }

    public static <T> void writeExcelToFile(String sheetName, List<T> list, File file) throws Exception {
        ByteArrayOutputStream bos = writeExcel(sheetName, list);
        if (bos == null) {
            throw new RuntimeException("no data to write");
        }
        try (OutputStream outputStream = new FileOutputStream(file)) {
            bos.writeTo(outputStream);
        }
    }

    private static void checkFieldClass(Class<?> clazz) {
        for (Class<?> supprtClass : SUPPORT_FIELD_CLASSES) {
            if (supprtClass == clazz) {
                return;
            }
        }
        throw new RuntimeException("Unsupport Field Class " + clazz.getSimpleName());
    }

    public static <T> void writeMoreSheetExcelToFile(String sheetName, List<T> list, File file, int startRow, int startCol) throws Exception {
        if (list == null || list.size() == 0) {
            return;
        }
        Workbook wb;
        if (!file.exists()) {
            wb = new XSSFWorkbook();
        } else {
            wb = new XSSFWorkbook(new FileInputStream(file));
        }
        T test = list.get(0);
        Map<String, Field> fieldMap = new HashMap<>();
        List<String> titles = new ArrayList<>();
        List<Field> fields = ReflectionUtils.getFields(test.getClass());
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelCell.class)) {
                checkFieldClass(field.getType());
                ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
                fieldMap.put(excelCell.name(), field);
                titles.add(excelCell.name());
            }
        }
        if (fieldMap.size() != titles.size()) {
            wb.close();
            throw new IllegalArgumentException("@ExcelCell name cannot same");
        }
        //
        Sheet sheet = wb.getSheet(sheetName);
        if (sheet == null) {
            sheet = wb.createSheet(sheetName);
        }
        //set header
        Row titleRow = sheet.createRow(startRow);
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = titleRow.createCell(i + startCol);
            cell.setCellValue(titles.get(i));
        }
        //
        int totalRowCount = list.size();
        for (int r = 0; r < totalRowCount; r++) {
            T bean = list.get(r);
            Row row = sheet.createRow(r + 1 + startRow);
            for (int c = 0; c < titles.size(); c++) {
                Cell cell = row.createCell(c + startCol);
                String title = titles.get(c);
                Field field = fieldMap.get(title);
                field.setAccessible(true);
                Object val = field.get(bean);
                if (val == null) {
                    continue;
                }
                ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
                if (field.getType() == Date.class) {
                    cell.setCellValue(new SimpleDateFormat(excelCell.dateFormat()).format((Date) val));
                } else {
                    cell.setCellValue(val.toString());
                }
            }
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        bos.close();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            bos.writeTo(outputStream);
        }
        wb.close();
    }

    public static <T> ByteArrayOutputStream writeExcel(String sheetName, List<T> list) throws Exception {
        if (list == null || list.size() == 0) {
            return null;
        }
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            writeExcel0(wb, sheetName, list);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            bos.close();
            return bos;
        }
    }

    public static ByteArrayOutputStream writeExcel(TableData data) throws IOException {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFSheet sheet = wb.createSheet(data.sheetName);
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < data.headers.size(); i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(data.headers.get(i));
            }
            //
            int idx = 1;
            for (List<String> ss : data.contents) {
                XSSFRow r = sheet.createRow(idx++);
                for (int i = 0; i < ss.size(); i++) {
                    XSSFCell cell = r.createCell(i);
                    cell.setCellValue(ss.get(i));
                }
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            bos.close();
            return bos;
        }
    }

    public static void addPicture(File xlsxFile, byte[] imageData,
                                  int col1, int row1, int col2, int row2) throws IOException {
        try (XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsxFile))) {
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, col1, row1, col2, row2);
            drawing.createPicture(anchor, wb.addPicture(imageData, XSSFWorkbook.PICTURE_TYPE_JPEG));
            FileOutputStream fileOut = new FileOutputStream(xlsxFile);
            wb.write(fileOut);
            fileOut.close();
        }
    }

    public static <T> List<T> readExcel(Class<T> clazz, InputStream in) throws Exception {
        try (XSSFWorkbook wb = new XSSFWorkbook(in)) {
            if (wb.getNumberOfSheets() <= 0) {
                return new ArrayList<>();
            }
            XSSFSheet sheet = wb.getSheetAt(0);
            //
            List<T> result = new ArrayList<>(sheet.getLastRowNum() - 1);
            Row row = sheet.getRow(sheet.getFirstRowNum());
            //
            Map<String, Field> fieldMap = new HashMap<>();
            Map<String, String> titleMap = new HashMap<>();
            //
            List<Field> fields = ReflectionUtils.getFields(clazz);
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelCell.class)) {
                    ExcelCell mapperCell = field.getAnnotation(ExcelCell.class);
                    fieldMap.put(mapperCell.name(), field);
                }
            }

            for (Cell title : row) {
                CellReference cellRef = new CellReference(title);
                titleMap.put(cellRef.getCellRefParts()[2], title.getRichStringCellValue().getString());
            }

            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                T t = clazz.newInstance();
                Row dataRow = sheet.getRow(i);
                for (Cell data : dataRow) {
                    CellReference cellRef = new CellReference(data);
                    String cellTag = cellRef.getCellRefParts()[2];
                    String name = titleMap.get(cellTag);
                    Field field = fieldMap.get(name);
                    if (null != field) {
                        field.setAccessible(true);
                        setFiedlValue(data, t, field);
                    }
                }
                result.add(t);
            }
            //
            return result;
        }
    }

    public static TableData readExcel(InputStream in) throws Exception {
        TableData data = new TableData();
        try (XSSFWorkbook wb = new XSSFWorkbook(in)) {
            if (wb.getNumberOfSheets() <= 0) {
                return data;
            }
            XSSFSheet sheet = wb.getSheetAt(0);
            data.sheetName = sheet.getSheetName();
            if (sheet.getLastRowNum() <= 0) {
                return data;
            }
            XSSFRow row = sheet.getRow(sheet.getFirstRowNum());
            for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
                XSSFCell cell = row.getCell(i);
                data.headers.add(getCellContent(cell));
            }
            //
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                List<String> cc = new ArrayList<>();
                data.contents.add(cc);
                XSSFRow rr = sheet.getRow(i);
                for (int j = rr.getFirstCellNum(); j <= rr.getLastCellNum(); j++) {
                    XSSFCell cell = rr.getCell(j);
                    cc.add(getCellContent(cell));
                }
            }
        }
        return data;
    }

    private static String getCellContent(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.BOOLEAN) {
                return (cell.getBooleanCellValue() + "");
            }
            if (cell.getCellType() == CellType.NUMERIC) {
                return (String.valueOf(cell.getNumericCellValue()));
            }
            if (cell.getCellType() == CellType.STRING) {
                return (cell.getStringCellValue() + "");
            }
        }
        return "";
    }

    private static void setFiedlValue(Cell cell, Object o, Field field) throws IllegalAccessException, ParseException {
        String content = getCellContent(cell);
        if (content.length() == 0) {
            return;
        }
        log.debug("cell:{}, field:{}, type:{} content:{}",
                cell.getCellType(), field.getName(), field.getType().getName(), content);
        Class<?> filedClass = field.getType();
        if (filedClass == long.class || filedClass == Long.class) {
            field.set(o, Long.valueOf(content));
        } else if (filedClass == int.class || filedClass == Integer.class) {
            field.set(o, Integer.valueOf(content));
        } else if (filedClass == short.class || filedClass == Short.class) {
            field.set(o, Short.valueOf(content));
        } else if (filedClass == float.class || filedClass == Float.class) {
            field.set(o, Float.valueOf(content));
        } else if (filedClass == double.class || filedClass == Double.class) {
            field.set(o, Double.valueOf(content));
        } else if (filedClass == byte.class || filedClass == Byte.class) {
            field.set(o, Byte.valueOf(content));
        } else if (filedClass == boolean.class || filedClass == Boolean.class) {
            field.set(o, Boolean.valueOf(content));
        } else if (filedClass == Date.class) {
            ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
            field.set(o, new SimpleDateFormat(excelCell.dateFormat()).parse(content));
        } else if (filedClass == String.class) {
            field.set(o, content);
        }
    }
}
