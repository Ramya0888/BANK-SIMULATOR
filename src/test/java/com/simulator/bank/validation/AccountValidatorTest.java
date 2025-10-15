package com.simulator.bank.validation;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class AccountValidatorTest {

    @Test
    void testAccountType() {
        assertTrue(AccountValidator.isValidAccountType("SAVINGS"));
        assertFalse(AccountValidator.isValidAccountType("LOAN"));
    }

    @Test
    void testBankAndAccNumber() {
        assertTrue(AccountValidator.isValidBankName("SBI"));
        assertFalse(AccountValidator.isValidBankName(""));

        assertTrue(AccountValidator.isValidAccountNumber("ACC1001"));
        assertFalse(AccountValidator.isValidAccountNumber(""));
    }

    @Test
    void testBalanceAndIFSC() {
        assertTrue(AccountValidator.isValidBalance(new BigDecimal("1000")));
        assertFalse(AccountValidator.isValidBalance(new BigDecimal("-5")));

        assertTrue(AccountValidator.isValidIFSC("SBIN0001111"));
        assertFalse(AccountValidator.isValidIFSC("12345"));
    }

    @Test
    void testPhoneLinkedStatus() {
        assertTrue(AccountValidator.isValidPhoneLinked("9876543210"));
        assertFalse(AccountValidator.isValidPhoneLinked("abc"));

        assertTrue(AccountValidator.isValidStatus("ACTIVE"));
        assertFalse(AccountValidator.isValidStatus("DISABLED"));
    }
}
