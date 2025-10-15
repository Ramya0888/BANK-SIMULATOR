package com.simulator.bank.validation;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionValidatorTest {


    @Test
    void testValidAmount() {
        assertDoesNotThrow(() -> TransactionValidator.validateAmount(new BigDecimal("100")));
    }

    @Test
    void testInvalidAmountZero() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> TransactionValidator.validateAmount(BigDecimal.ZERO));
        assertEquals("Amount must be greater than zero", ex.getMessage());
    }

    @Test
    void testInvalidAmountNegative() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> TransactionValidator.validateAmount(new BigDecimal("-50")));
        assertEquals("Amount must be greater than zero", ex.getMessage());
    }

  
    @Test
    void testValidMode() {
        assertDoesNotThrow(() -> TransactionValidator.validateMode("CASH"));
        assertDoesNotThrow(() -> TransactionValidator.validateMode("online")); // lowercase works
    }

    @Test
    void testInvalidMode() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> TransactionValidator.validateMode("BITCOIN"));
        assertTrue(ex.getMessage().contains("Invalid transaction mode"));
    }

    @Test
    void testValidType() {
        assertDoesNotThrow(() -> TransactionValidator.validateType("CREDIT"));
        assertDoesNotThrow(() -> TransactionValidator.validateType("debit"));
    }

    @Test
    void testInvalidType() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> TransactionValidator.validateType("SOMETHING"));
        assertTrue(ex.getMessage().contains("Invalid transaction type"));
    }
}
