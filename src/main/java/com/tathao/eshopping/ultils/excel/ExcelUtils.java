package com.tathao.eshopping.ultils.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Calendar;
import java.util.List;

public class ExcelUtils {

    public static String export(List<Object[]> listValue, String templatePath, String outputPath, String fileName) throws IOException {
        //1. read template
        FileInputStream fileInputStream = new FileInputStream(new File(templatePath));

        //2. create sheet
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(false);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        //3. write content for sheet
        int rowIndex = 3;

        for(int i = 0; i < listValue.size(); i++) {
            XSSFRow row = sheet.createRow(rowIndex);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(rowIndex - 2);
            cell.setCellStyle(cellStyle);

            Object[] item = listValue.get(i);

            int positionNextCell = 0;
            for(int j = 0; j < item.length; j++) {
                addCellValue(row, positionNextCell + j, (String) item[j], cellStyle);
            }
            rowIndex++;
        }

        //4. write file
        Calendar cal = Calendar.getInstance();
        fileName += cal.get(Calendar.DAY_OF_YEAR) + "_" + System.currentTimeMillis() + ".xlsx";
        outputPath += fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputPath));
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        return outputPath;
    }

    private static void addCellValue(XSSFRow row, Integer position, String value, XSSFCellStyle cellStyle) {
        XSSFCell cell = row.createCell(position);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

}
