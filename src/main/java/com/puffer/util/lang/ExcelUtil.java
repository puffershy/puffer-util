package com.puffer.util.lang;

import com.csvreader.CsvReader;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * excel工具类
 *
 * @author buyi
 * @date 2019年01月21日 09:44:26
 * @since 1.0.0
 */
public class ExcelUtil {
    public static final String EXCEL_SUFFIX_CSV = "csv";
    public static final String EXCEL_SUFFIX_XLS = "xls";
    public static final String EXCEL_SUFFIX_XLSX = "xlsx";

    /**
     * 读取excle
     *
     * @param filePath 文件路径
     * @param skipLine 跳过的行数
     * @return java.util.List<java.lang.Object [ ]>
     * @author buyi
     * @date 2019年01月21日 14:23:46
     * @since 1.0.0
     */
    public static List<Object[]> readFile(String filePath, int skipLine) throws IOException {

        List<Object[]> list = Lists.newArrayList();

        File file = new File(filePath);

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook workbook = getWorkBook(fileInputStream, file);

            //取第一个
            Sheet sheet = workbook.getSheetAt(0);

            int count = 0;
            for (Row row : sheet) {
                if (count < skipLine) {
                    //跳过指定的行数
                    count++;
                    continue;
                }

                //获取最大列
                int lastCellNum = row.getLastCellNum();
                Object[] values = new Object[lastCellNum];
                for (int i = 0; i < lastCellNum; i++) {
                    Cell cell = row.getCell(i);

                    Object value = getValue(cell);

                    values[i] = value;
                }

                list.add(values);
            }

        }

        return list;
    }

    public static List<Object[]> readFile(InputStream inputStream, int skipLine, String fileSuffix) throws Exception {
        if(fileSuffix.endsWith(EXCEL_SUFFIX_CSV)){
            //如果是csv
            return readFileCsv(inputStream,skipLine);
        }


        List<Object[]> list = Lists.newArrayList();
        Workbook workbook = getWorkBook(inputStream, fileSuffix);

        //取第一个
        Sheet sheet = workbook.getSheetAt(0);

        int count = 0;
        for (Row row : sheet) {
            if (count < skipLine) {
                //跳过指定的行数
                count++;
                continue;
            }

            //获取最大列
            int lastCellNum = row.getLastCellNum();
            Object[] values = new Object[lastCellNum];
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = row.getCell(i);

                Object value = getValue(cell);

                values[i] = value;
            }

            list.add(values);
        }

        return list;
    }

    private static List<Object[]> readFileCsv(InputStream inputStream, int skipLine) throws IOException {
        int i = 1;
        List<Object[]> list = Lists.newArrayList();
        CsvReader csvReader = new CsvReader(inputStream, Charset.forName("UTF-8"));
        while (csvReader.readRecord()) {
            if(i++ <= skipLine){
                continue;
            }

            if(StringUtils.isBlank(csvReader.getRawRecord().trim())){
                continue;
            }

            list.add(csvReader.getRawRecord().split("\t"));
        }

        return list;

    }

    /**
     * 获取值
     *
     * @param cell
     * @return java.lang.Object
     * @author buyi
     * @date 2019年01月21日 14:23:35
     * @since 1.0.0
     */
    private static Object getValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        Object obj = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_ERROR:
                obj = cell.getErrorCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;

    }

    /**
     * 获取excle workbook
     *
     * @param fileInputStream
     * @param file
     * @return org.apache.poi.ss.usermodel.Workbook
     * @author buyi
     * @date 2019年01月21日 14:23:26
     * @since 1.0.0
     */
    private static Workbook getWorkBook(FileInputStream fileInputStream, File file) throws IOException {
        if (file.getName().endsWith(EXCEL_SUFFIX_XLS)) {
            return new HSSFWorkbook(fileInputStream);
        } else if (file.getName().endsWith(EXCEL_SUFFIX_XLSX)
                || file.getName().endsWith(EXCEL_SUFFIX_CSV)) {
            return new XSSFWorkbook(fileInputStream);
        }

        return null;
    }

    private static Workbook getWorkBook(InputStream inputStream, String fileSuffix) throws IOException, InvalidFormatException {
        if (fileSuffix.endsWith(EXCEL_SUFFIX_XLS)) {
            return new HSSFWorkbook(inputStream);
        } else if (fileSuffix.endsWith(EXCEL_SUFFIX_XLSX)
                || fileSuffix.endsWith(EXCEL_SUFFIX_CSV)) {
            return new XSSFWorkbook(inputStream);
        }

        return null;
    }

    public static byte[] createFile(List<String[]> lines) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < lines.size(); i++) {
            XSSFRow row = sheet.createRow(i);
            String[] strings = lines.get(i);
            for (int i1 = 0; i1 < strings.length; i1++) {
                XSSFCell cell = row.createCell(i1);
                cell.setCellValue(strings[i1]);

                if (i == 0) {
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xssfWorkbook.write(outputStream);

        return outputStream.toByteArray();
    }

    public static boolean createFile(String filePath, List<String[]> lines) throws IOException {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);) {
            XSSFSheet sheet = xssfWorkbook.createSheet();

            XSSFFont font = xssfWorkbook.createFont();
            font.setBold(true);
            // font.setColor(IndexedColors.BLUE.getIndex());
            XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // cellStyle.setFillBackgroundColor();

            for (int i = 0; i < lines.size(); i++) {
                XSSFRow row = sheet.createRow(i);
                String[] strings = lines.get(i);
                for (int i1 = 0; i1 < strings.length; i1++) {
                    XSSFCell cell = row.createCell(i1);
                    cell.setCellValue(strings[i1]);

                    if (i == 0) {
                        cell.setCellStyle(cellStyle);
                    }
                }
            }

            // 设置宽度自适应
            // for (int o = 0; o < 5; o++) {
            //     sheet.autoSizeColumn(o, true);
            //     sheet.setColumnWidth(o, sheet.getColumnWidth(o) * 17 / 10);
            // }

            xssfWorkbook.write(fileOutputStream);
        }

        return true;
    }
}
