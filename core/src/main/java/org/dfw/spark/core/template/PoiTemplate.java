package org.dfw.spark.core.template;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dfw.spark.core.annotation.Nullable;
import org.dfw.spark.core.constant.Constant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * POI工具组合
 */
public class PoiTemplate {
    /**
     * 解析XLS文件，<strong>解析的时候，忽略空格和空行，且所有的字符串都经过trim处理</strong>
     *
     * @param is           xls流
     * @param maxRowNum    最大行数
     * @param maxColumnNum 最大列数,<strong>注意，这个数值会影响行List的长度，也就是内存大小</strong>
     * @return 解析完成的数据
     * @throws Exception 待处理异常
     */
    public List<String[]> parseXLS(InputStream is, int maxRowNum, int maxColumnNum) throws Exception {
        List<String[]> retTable = new LinkedList<String[]>();
        //构建工作簿，仅仅支持xls格式
        Workbook wb = new HSSFWorkbook(is);
        //获得第一个表单
        Sheet sheet = wb.getSheetAt(0);
        //获得第一个表单的迭代器
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            //获得行数据
            Row row = rows.next();
            //检测是否超出预定义的行数,因为从0开始，所以计数的时候，需要减去1
            if (row.getRowNum() > (maxRowNum - 1)) {
                throw new IndexOutOfBoundsException(String.format("row num out of define max row num %s", String.valueOf(maxRowNum)));
            }
            //构建返回row
            String[] retRow = new String[maxColumnNum];
            //获取每个cell
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                String val = null;
                //根据cell中的类型来输出数据
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        //处理Date类型
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            //获取成DATE类型
                            Date dt = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                            //转换为long类型
                            val = String.valueOf(dt.getTime());
                        } else {
                            double number = cell.getNumericCellValue();
                            if (number == (int) number) {
                                //整数
                                val = String.valueOf((int) number);
                            } else if (number == (long) number) {
                                //long类型
                                val = String.valueOf((long) number);
                            } else {
                                //包含小数的double
                                val = String.valueOf(cell.getNumericCellValue());
                            }
                        }
                        break;
                    case Cell.CELL_TYPE_STRING:
                        val = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        val = String.valueOf(cell.getBooleanCellValue());
                        break;
                }
                //如果无法解析的数值类型，则忽略
                if (val != null) {
                    //判断是否超出列数，因为从0开始，所以计数的时候，需要减去1
                    if (cell.getColumnIndex() > (maxColumnNum - 1)) {
                        throw new IndexOutOfBoundsException(String.format("column num out of define max column num %s", String.valueOf(maxColumnNum)));
                    }
                    val = val.trim();
                    //避免空字符串
                    if (!Constant.EMPTY.equals(val)) {
                        retRow[cell.getColumnIndex()] = val;
                    }
                }
            }
            //检测，所有的数据是否都为null，如果是，则忽略
            boolean pass = false;
            for (String v : retRow) {
                if (v != null) {
                    pass = true;
                    break;
                }
            }
            if (pass) {
                //添加到表中
                retTable.add(retRow);
            }
        }
        return retTable;
    }

    /**
     * 构建一个 xls，完整刻录整个data数据，<strong>不忽略空行和空格。</strong>
     *
     * @param data 需要记录的数据
     * @return 返回二进制，如果转换失败，则返回null
     */
    @Nullable
    public byte[] makeXLS(List<String[]> data) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet0");
        //当前rowCount
        int rowIndex = -1;
        for (String[] rowData : data) {
            //自增
            rowIndex++;
            Row row = sheet.createRow(rowIndex);
            for (int columnIndex = 0; columnIndex < rowData.length; ++columnIndex) {
                if (rowData[columnIndex] != null) {
                    Cell cell = row.createCell(columnIndex);
                    cell.setCellValue(rowData[columnIndex]);
                }
            }
        }
        //读取数据
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 1024 * 3);
            wb.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * 替换给定XLS中的字符串，如果失败，则返回null
     *
     * @param xls      XLS输入
     * @param tokenMap 待替换的Token
     * @return 如果失败，则返回null
     */
    @Nullable
    public byte[] replaceXls(byte[] xls, Map<String, String> tokenMap) {
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(new ByteArrayInputStream(xls));
            HSSFSheet sheet = wb.getSheetAt(0);
            // 获取行数
            int rowNum = sheet.getLastRowNum();
            for (int i = 0; i < rowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                // 获取行里面的总列数
                int columnNum = 0;
                if (row != null) {
                    columnNum = row.getPhysicalNumberOfCells();
                }
                for (int j = 0; j < columnNum; j++) {
                    HSSFCell cell = sheet.getRow(i).getCell(j);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING: {
                                // 读取单元数值
                                String cellValue = cell.getStringCellValue();
                                // 读取是否存在相对应的token
                                String token = tokenMap.get(cellValue);
                                if (token != null) {
                                    cell.setCellValue(token);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        //读取数据
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 1024 * 3);
            wb.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }
}
