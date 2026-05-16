package com.feedback.service;

import com.feedback.entity.Feedback;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExcelService {

    // ✅ Predictable absolute path on Desktop (works on Windows/Mac/Linux)
    private static final String FILEPATH =
            System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "feedbacks.xlsx";

    public void saveFeedbackToExcel(Feedback feedback) {

        XSSFWorkbook workbook = null;

        try {
            XSSFSheet sheet;
            File file = new File(FILEPATH);

            if (file.exists() && file.length() > 0) {
                workbook = new XSSFWorkbook(file);
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Feedbacks");

                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Name");
                header.createCell(1).setCellValue("Email");
                header.createCell(2).setCellValue("Rating");
                header.createCell(3).setCellValue("Comments");
            }

            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);

            row.createCell(0).setCellValue(feedback.getName() != null ? feedback.getName() : "");
            row.createCell(1).setCellValue(feedback.getEmail() != null ? feedback.getEmail() : "");
            row.createCell(2).setCellValue(feedback.getRating() != null ? feedback.getRating() : 0);
            row.createCell(3).setCellValue(feedback.getComments() != null ? feedback.getComments() : "");

            try (FileOutputStream fos = new FileOutputStream(FILEPATH)) {
                workbook.write(fos);
            }

            System.out.println("✅ Excel saved at: " + FILEPATH);

        } catch (IOException | InvalidFormatException e) {
            System.out.println("❌ Excel save error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ignored) {}
            }
        }
    }
}