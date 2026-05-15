package com.feedback.util;

import com.feedback.entity.Feedback;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelExporter {

    public static void export(List<Feedback> list) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Feedback Data");

        // Create Header Row
        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Email");
        header.createCell(3).setCellValue("Rating");
        header.createCell(4).setCellValue("Comments");

        int rowNum = 1;

        // Fill Data Rows
        for (Feedback f : list) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(f.getId() != null ? f.getId() : 0);
            row.createCell(1).setCellValue(f.getName() != null ? f.getName() : "");
            row.createCell(2).setCellValue(f.getEmail() != null ? f.getEmail() : "");
            row.createCell(3).setCellValue(f.getRating() != null ? f.getRating() : 0);
            row.createCell(4).setCellValue(f.getComments() != null ? f.getComments() : "");
        }

        // Auto-size columns (important for Excel readability)
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Save file safely
        String filePath = System.getProperty("user.home") + "/Desktop/feedback.xlsx";

        try (FileOutputStream out = new FileOutputStream(filePath)) {

            workbook.write(out);
            workbook.close();

            System.out.println("Excel file created successfully at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}