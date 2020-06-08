package com.puffer.util.lang;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * excel工具类
 *
 * @author buyi
 * @date 2019年01月21日 09:44:26
 * @since 1.0.0
 */
public class ExcelUtil {
    private static final String EXCEL_SUFFIX_XLS = "xls";
    private static final String EXCEL_SUFFIX_XLSX = "xlsx";

    /**
     * 读取excle
     *
     * @param filePath 文件路径
     * @param skipLine 跳过的行数
     * @return java.util.List<java.lang.Object       [       ]>
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
        if(cell == null){
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
        } else if (file.getName().endsWith(EXCEL_SUFFIX_XLSX)) {
            return new XSSFWorkbook(fileInputStream);
        }

        return null;
    }

}
