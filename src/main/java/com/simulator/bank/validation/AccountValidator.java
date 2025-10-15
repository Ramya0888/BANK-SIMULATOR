package com.simulator.bank.validation;

import com.simulator.bank.model.Account;
import java.math.BigDecimal;

public class AccountValidator {

   

    public static boolean isValidAccountType(String type) {
        return type != null && (type.equalsIgnoreCase("SAVINGS") || type.equalsIgnoreCase("CURRENT"));
    }

    public static boolean isValidBankName(String bankName) {
        return bankName != null && !bankName.trim().isEmpty() && bankName.matches("^[A-Za-z0-9 ]+$");
    }

    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("^ACC[0-9]{4,}$");
    }

    public static boolean isValidBalance(BigDecimal balance) {
        return balance != null && balance.compareTo(BigDecimal.ZERO) >= 0;
    }

    public static boolean isValidIFSC(String ifsc) {
        return ifsc != null && ifsc.matches("^[A-Z]{4}0[A-Z0-9]{6}$");
    }

    public static boolean isValidPhoneLinked(String phone) {
        return phone != null && phone.matches("^[0-9]{10}$");
    }

    public static boolean isValidStatus(String status) {
        return status != null && (status.equalsIgnoreCase("ACTIVE") || status.equalsIgnoreCase("INACTIVE"));
    }

    
    public static void validate(Account account) throws IllegalArgumentException {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (!isValidAccountType(account.getAccountType())) {
            throw new IllegalArgumentException("Invalid account type: " + account.getAccountType());
        }
        if (!isValidBankName(account.getBankName())) {
            throw new IllegalArgumentException("Invalid bank name: " + account.getBankName());
        }
        if (!isValidBalance(account.getBalance())) {
            throw new IllegalArgumentException("Balance must be non-negative");
        }
        if (!isValidIFSC(account.getIfscCode())) {
            throw new IllegalArgumentException("Invalid IFSC code: " + account.getIfscCode());
        }
        if (!isValidPhoneLinked(account.getPhoneLinked())) {
            throw new IllegalArgumentException("Invalid phone number: " + account.getPhoneLinked());
        }
        if (!isValidStatus(account.getStatus())) {
            throw new IllegalArgumentException("Invalid account status: " + account.getStatus());
        }
    }
}
