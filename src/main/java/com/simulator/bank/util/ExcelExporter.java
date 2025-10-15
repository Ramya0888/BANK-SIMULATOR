package com.simulator.bank.util;

import com.simulator.bank.model.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.format.DateTimeFormatter;
import java.util.List;

public final class ExcelExporter {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ExcelExporter() {}

    public static Workbook createWorkbook(List<Transaction> txs) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Transactions");


        Row header = sheet.createRow(0);
        String[] headers = {
            "Transaction ID", "Transaction Date", "Amount",
            "Sender Account", "Receiver Account",
            "Balance After", "Type", "Mode", "Description"
        };
        CellStyle headerStyle = wb.createCellStyle();
        Font bold = wb.createFont();
        bold.setBold(true);
        headerStyle.setFont(bold);

        for (int i = 0; i < headers.length; i++) {
            Cell c = header.createCell(i);
            c.setCellValue(headers[i]);
            c.setCellStyle(headerStyle);
        }

     
        int r = 1;
        for (Transaction t : txs) {
            Row row = sheet.createRow(r++);
            row.createCell(0).setCellValue(t.getTransactionId() == null ? 0 : t.getTransactionId());
            row.createCell(1).setCellValue(
                    t.getTransactionDate() == null ? "" : t.getTransactionDate().format(DATE_FMT)
            );
            row.createCell(2).setCellValue(
                    t.getTransactionAmount() == null ? 0.0 : t.getTransactionAmount().doubleValue()
            );
            row.createCell(3).setCellValue(t.getSenderAccountNumber() == null ? "" : t.getSenderAccountNumber());
            row.createCell(4).setCellValue(t.getReceiverAccountNumber() == null ? "" : t.getReceiverAccountNumber());
            row.createCell(5).setCellValue(
                    t.getBalanceAmount() == null ? 0.0 : t.getBalanceAmount().doubleValue()
            );
            row.createCell(6).setCellValue(t.getTransactionType() == null ? "" : t.getTransactionType());
            row.createCell(7).setCellValue(t.getModeOfTransaction() == null ? "" : t.getModeOfTransaction());
            row.createCell(8).setCellValue(t.getDescription() == null ? "" : t.getDescription());
        }

      
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return wb;
    }
}
