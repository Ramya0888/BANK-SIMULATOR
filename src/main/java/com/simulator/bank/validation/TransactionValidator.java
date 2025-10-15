package com.simulator.bank.validation;

import java.math.BigDecimal;
import java.util.Set;

public class TransactionValidator {

    private static final Set<String> VALID_TYPES = Set.of("CREDIT", "DEBIT");
    private static final Set<String> VALID_MODES = Set.of("CASH", "ONLINE");

    public static void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    public static void validateMode(String mode) {
        if (mode == null || !VALID_MODES.contains(mode.toUpperCase())) {
            throw new IllegalArgumentException("Invalid transaction mode");
        }
    }

    public static void validateType(String type) {
        if (type == null || !VALID_TYPES.contains(type.toUpperCase())) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
    }
}
