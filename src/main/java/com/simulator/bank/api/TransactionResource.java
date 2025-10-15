package com.simulator.bank.api;

import com.simulator.bank.dao.AccountJdbcDao;
import com.simulator.bank.dao.TransactionDao;
import com.simulator.bank.model.Transaction;
import com.simulator.bank.service.TransactionService;
import com.simulator.bank.config.DataSourceFactory;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    private final TransactionService service;

    public TransactionResource() {
        var ds = DataSourceFactory.getDataSource();
        var accountDao = new AccountJdbcDao(ds);
        var transactionDao = new TransactionDao();
        this.service = new TransactionService(accountDao, transactionDao);
    }

    //Deposit
    @POST
    @Path("/deposit")
    public Response deposit(Map<String, Object> body) {
        try {
            String accNum = (String) body.get("account");
            BigDecimal amount = new BigDecimal(body.get("amount").toString());
            String mode = (String) body.get("mode");

            Transaction tx = service.deposit(accNum, amount, mode);
            return Response.ok(tx).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    //Withdraw
    @POST
    @Path("/withdraw")
    public Response withdraw(Map<String, Object> body) {
        try {
            String accNum = (String) body.get("account");
            BigDecimal amount = new BigDecimal(body.get("amount").toString());
            String mode = (String) body.get("mode");
            Integer pin = Integer.parseInt(body.get("pin").toString()); 

            Transaction tx = service.withdraw(accNum, amount, mode, pin); 
            return Response.ok(tx).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    //Transfer
    @POST
    @Path("/transfer")
    public Response transfer(Map<String, Object> body) {
        try {
            String fromAcc = (String) body.get("from");
            String toAcc = (String) body.get("to");
            BigDecimal amount = new BigDecimal(body.get("amount").toString());
            String mode = (String) body.get("mode");
            Integer pin = Integer.parseInt(body.get("pin").toString()); 

            Transaction tx = service.transfer(fromAcc, toAcc, amount, mode, pin); 
            return Response.ok(tx).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    //Balance
    @GET
    @Path("/balance/{account}")
    public Response getBalance(@PathParam("account") String accNum) {
        try {
            BigDecimal balance = service.getBalance(accNum);
            return Response.ok(balance).build();
        } catch (SQLException | IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    // Transaction History (JSON) 
    @GET
    @Path("/{account}")
    public Response getTransactions(@PathParam("account") String accNum) {
        try {
            List<Transaction> txs = service.getTransactions(accNum);
            return Response.ok(txs).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching transactions").build();
        }
    }

    //Transaction History (Excel Download) 
    @GET
    @Path("/{account}/download")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response downloadTransactionsExcel(@PathParam("account") String accNum) {
        try {
            List<Transaction> txs = service.getTransactions(accNum);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Transactions");

            // Header row
            Row header = sheet.createRow(0);
            String[] columns = {
                "Transaction ID", "Date", "Type", "Mode", "Amount",
                "Sender Account", "Receiver Account", "Balance", "Description"
            };

            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Data rows
            int rowNum = 1;
            for (Transaction tx : txs) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(tx.getTransactionId());
                row.createCell(1).setCellValue(tx.getTransactionDate().toString());
                row.createCell(2).setCellValue(tx.getTransactionType());
                row.createCell(3).setCellValue(tx.getModeOfTransaction());
                row.createCell(4).setCellValue(
                        tx.getTransactionAmount() != null ? tx.getTransactionAmount().doubleValue() : 0
                );
                row.createCell(5).setCellValue(
                        tx.getSenderAccountNumber() != null ? tx.getSenderAccountNumber() : ""
                );
                row.createCell(6).setCellValue(
                        tx.getReceiverAccountNumber() != null ? tx.getReceiverAccountNumber() : ""
                );
                row.createCell(7).setCellValue(
                        tx.getBalanceAmount() != null ? tx.getBalanceAmount().doubleValue() : 0
                );
                row.createCell(8).setCellValue(
                        tx.getDescription() != null ? tx.getDescription() : ""
                );
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();

            return Response.ok(out.toByteArray())
                    .header("Content-Disposition", "attachment; filename=transactions_" + accNum + ".xlsx")
                    .build();

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching transactions").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generating Excel file: " + e.getMessage()).build();
        }
    }
}
